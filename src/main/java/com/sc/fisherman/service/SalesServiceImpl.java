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

import java.io.IOException;
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
        var sales = repository.findById(salesId).orElseThrow(() -> new NotFoundException(salesId.toString()));
        try {
            List<String> imageUrlList = new ArrayList<>();
            for (var file : fileList) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadPath, fileName);

                Files.createDirectories(filePath.getParent());

                Files.write(filePath, file.getBytes());

                String imageUrl = "/fisherman/uploads/" + fileName;
                imageUrlList.add(imageUrl);
            }
            sales.setImageUrlList(imageUrlList);
            repository.save(sales);
        } catch (IOException e) {
            throw new AnErrorOccurredException(salesId.toString());
        }
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
