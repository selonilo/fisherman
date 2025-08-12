package com.sc.fisherman.service;

import com.sc.fisherman.exception.AlreadyExistException;
import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.community.CommunityModel;
import com.sc.fisherman.model.entity.CommunityEntity;
import com.sc.fisherman.model.entity.FollowEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.mapper.CommunityMapper;
import com.sc.fisherman.repository.*;
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
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private CommunityRepository repository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public CommunityModel save(CommunityModel model) {
        var optEntity = repository.findByName(model.getName());
        if (optEntity.isPresent()) {
            throw new AlreadyExistException(model.getName());
        }
        var savedModel = CommunityMapper.mapTo(repository.saveAndFlush(CommunityMapper.mapTo(model)));
        var optUser = userRepository.findById(savedModel.getUserId());
        optUser.ifPresent(user -> {
            FollowEntity followEntity = new FollowEntity();
            followEntity.setContentId(savedModel.getId());
            followEntity.setUserId(user.getId());
            followEntity.setContentType(EnumContentType.COMMUNITY);
            followRepository.saveAndFlush(followEntity);
        });
        if (model.getFile() != null) {
            uploadImage(savedModel.getId(), model.getFile());
        }
        return savedModel;
    }

    public CommunityModel update(CommunityModel model) {
        var optEntity = repository.findById(model.getId());
        if (optEntity.isPresent()) {
            var entity = CommunityMapper.mapTo(model);
            entity.setImageUrl(optEntity.get().getImageUrl());
            return CommunityMapper.mapTo(repository.saveAndFlush(entity));
        } else {
            throw new NotFoundException(model.getName());
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public CommunityModel getById(Long id) {
        var optEntity = repository.findById(id);
        if (optEntity.isPresent()) {
            var entity = optEntity.get();
            var model = CommunityMapper.mapTo(entity);
            return model;
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public List<CommunityModel> getList(Long userId) {
        List<CommunityEntity> entityList = repository.findAll();
        var modelList = CommunityMapper.mapToList(entityList);
        for (var model : modelList) {
            model.setFollowedCount(followRepository.countByContentTypeAndContentId(EnumContentType.COMMUNITY, model.getId()));
            model.setIsFollowed(followRepository.findByContentTypeAndUserIdAndContentId(EnumContentType.COMMUNITY, userId, model.getId()).isPresent());
        }
        return modelList;
    }

    public List<CommunityModel> getListByFollowed(Long userId) {
        List<FollowEntity> followEntityList = followRepository.findAllByContentTypeAndUserId(EnumContentType.COMMUNITY, userId);
        var communityIdList = followEntityList.stream().map(FollowEntity::getContentId).toList();
        List<CommunityEntity> entityList = repository.findAllByIdIn(communityIdList);
        var modelList = CommunityMapper.mapToList(entityList);
        for (var model : modelList) {
            model.setFollowedCount(followRepository.countByContentTypeAndContentId(EnumContentType.COMMUNITY, model.getId()));
        }
        return modelList;
    }

    public Boolean followCommunity(Long communityId, Long userId) {
        var optEntity = repository.findById(communityId);
        var optUser = userRepository.findById(userId);
        if (optEntity.isPresent() && optUser.isPresent()) {
            FollowEntity followEntity = new FollowEntity();
            followEntity.setContentId(communityId);
            followEntity.setUserId(userId);
            followEntity.setContentType(EnumContentType.COMMUNITY);
            followRepository.saveAndFlush(followEntity);
            return true;
        } else {
            throw new NotFoundException(communityId.toString().concat(userId.toString()));
        }
    }

    public Boolean unFollowCommunity(Long communityId, Long userId) {
        var optEntity = repository.findById(communityId);
        var optUser = userRepository.findById(userId);
        if (optEntity.isPresent() && optUser.isPresent()) {
            var optCommunity = followRepository.findByContentTypeAndUserIdAndContentId(EnumContentType.COMMUNITY, userId, communityId);
            optCommunity.ifPresent(community -> followRepository.delete(community));
            return false;
        } else {
            throw new NotFoundException(communityId.toString().concat(userId.toString()));
        }
    }

    public String uploadImage(Long communityId, MultipartFile file) {
        var post = repository.findById(communityId)
                .orElseThrow(() -> new NotFoundException(communityId.toString()));

        try {
            // 📌 Tüm dosyaları JPG olarak kaydedeceğiz
            String fileName = System.currentTimeMillis() + ".jpg";
            Path filePath = Paths.get(uploadPath, fileName);
            Files.createDirectories(filePath.getParent());

            // 1️⃣ MultipartFile'i BufferedImage olarak oku
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new RuntimeException("Görsel okunamadı.");
            }

            // 2️⃣ PNG ise arka planı beyaza çevir (çünkü JPG transparan desteklemez)
            if ("png".equalsIgnoreCase(getFileExtension(file.getOriginalFilename()))) {
                BufferedImage newImage = new BufferedImage(
                        image.getWidth(), image.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = newImage.createGraphics();
                g2d.setColor(Color.WHITE); // Beyaz arka plan
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

            // 4️⃣ Veritabanına URL kaydet
            String imageUrl = "/fisherman/uploads/" + fileName;
            post.setImageUrl(imageUrl);
            repository.save(post);

            return imageUrl;

        } catch (IOException e) {
            throw new AnErrorOccurredException(communityId.toString());
        }
    }

    // 📌 Yardımcı metod
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public void deleteImage(Long postId) {
        var post = repository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId.toString()));

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            try {
                Path filePath = Paths.get(uploadPath, post.getImageUrl().replace("/fisherman/uploads/", ""));

                Files.deleteIfExists(filePath);

                post.setImageUrl(null);
                repository.save(post);
            } catch (IOException e) {
                throw new AnErrorOccurredException(postId.toString());
            }
        } else {
            throw new NotFoundException("Silinecek resim".concat(postId.toString()));
        }
    }
}
