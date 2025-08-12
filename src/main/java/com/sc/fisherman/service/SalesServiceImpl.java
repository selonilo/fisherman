package com.sc.fisherman.service;

import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.sales.SalesModel;
import com.sc.fisherman.model.entity.SalesEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.mapper.SalesMapper;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.FavoriteRepository;
import com.sc.fisherman.repository.SalesRepository;
import com.sc.fisherman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private SalesRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    public SalesModel save(SalesModel model) {
        var savedModel = SalesMapper.mapTo(repository.saveAndFlush(SalesMapper.mapTo(model)));
        if (model.getFileList() != null && !model.getFileList().isEmpty()) {
            uploadImage(savedModel.getId(), model.getFileList());
        }
        return savedModel;
    }

    public SalesModel update(SalesModel model) {
        var optEntity = repository.findById(model.getId());
        if (optEntity.isPresent()) {
            var entity = SalesMapper.mapTo(model);
            return SalesMapper.mapTo(repository.saveAndFlush(entity));
        } else {
            throw new NotFoundException(model.getTitle());
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SalesModel getById(Long id) {
        var optEntity = repository.findById(id);
        if (optEntity.isPresent()) {
            var entity = optEntity.get();
            var model = SalesMapper.mapTo(entity);
            var optUser = userRepository.findById(model.getUserId());
            optUser.ifPresent(x -> model.setUserModel(UserMapper.mapTo(x)));
            model.setFavoriteCount(favoriteRepository.countByContentTypeAndContentId(EnumContentType.SALES, model.getId()));
            return model;
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public List<SalesModel> getList() {
        List<SalesEntity> entityList = repository.findAll();
        var modelList = SalesMapper.mapToList(entityList);
        for (var model : modelList) {
            var optUser = userRepository.findById(model.getUserId());
            optUser.ifPresent(x -> model.setUserModel(UserMapper.mapTo(x)));
            model.setFavoriteCount(favoriteRepository.countByContentTypeAndContentId(EnumContentType.SALES, model.getId()));
        }
        return modelList;
    }


    public void uploadImage(Long salesId, List<MultipartFile> fileList) {
        var sales = repository.findById(salesId)
                .orElseThrow(() -> new NotFoundException(salesId.toString()));

        try {
            List<String> imageUrlList = new ArrayList<>();

            for (var file : fileList) {
                // 📌 Tüm dosyaları JPG olarak kaydedeceğiz
                String fileName = System.currentTimeMillis() + ".jpg";
                Path filePath = Paths.get(uploadPath, fileName);
                Files.createDirectories(filePath.getParent());

                // 1️⃣ MultipartFile'i BufferedImage olarak oku
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    throw new RuntimeException("Görsel okunamadı: " + file.getOriginalFilename());
                }

                // 2️⃣ PNG ise arka planı beyaza çevir
                if ("png".equalsIgnoreCase(getFileExtension(file.getOriginalFilename()))) {
                    BufferedImage newImage = new BufferedImage(
                            image.getWidth(), image.getHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );
                    Graphics2D g2d = newImage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
                    g2d.drawImage(image, 0, 0, null);
                    g2d.dispose();
                    image = newImage;
                }

                int targetWidth = 1080;
                if (image.getWidth() > targetWidth) {
                    int targetHeight = (int) (((double) targetWidth / image.getWidth()) * image.getHeight());
                    BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2dResize = resized.createGraphics();
                    g2dResize.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2dResize.drawImage(image, 0, 0, targetWidth, targetHeight, null);
                    g2dResize.dispose();
                    image = resized;
                }

                // 4️⃣ JPG olarak daha düşük kaliteyle kaydet (%30 kalite)
                try (OutputStream os = Files.newOutputStream(filePath)) {
                    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
                    writer.setOutput(ios);

                    ImageWriteParam param = writer.getDefaultWriteParam();
                    if (param.canWriteCompressed()) {
                        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        param.setCompressionQuality(0.3f); // %30 kalite
                    }

                    writer.write(null, new IIOImage(image, null, null), param);
                    ios.close();
                    writer.dispose();
                }

                // 4️⃣ URL listesine ekle
                String imageUrl = "/fisherman/uploads/" + fileName;
                imageUrlList.add(imageUrl);
            }

            // 5️⃣ Veritabanına kaydet
            sales.setImageUrlList(imageUrlList);
            repository.save(sales);

        } catch (IOException e) {
            throw new AnErrorOccurredException(salesId.toString());
        }
    }

    // 📌 Yardımcı metod
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public void deleteImage(Long salesId) {
        var sales = repository.findById(salesId)
                .orElseThrow(() -> new NotFoundException(salesId.toString()));

        if (sales.getImageUrlList() != null && !sales.getImageUrlList().isEmpty()) {
            try {
                for (var imageUrl : sales.getImageUrlList()) {
                    Path filePath = Paths.get(uploadPath, imageUrl.replace("/fisherman/uploads/", ""));
                    Files.deleteIfExists(filePath);
                }
                sales.setImageUrlList(null);
                repository.save(sales);
            } catch (IOException e) {
                throw new AnErrorOccurredException(salesId.toString());
            }
        } else {
            throw new NotFoundException("Silinecek fotoğraf".concat(salesId.toString()));
        }
    }
}
