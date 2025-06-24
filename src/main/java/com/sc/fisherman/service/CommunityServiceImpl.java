package com.sc.fisherman.service;

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

import java.io.IOException;
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
        var post = repository.findById(communityId).orElseThrow(() -> new NotFoundException(communityId.toString()));
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, fileName);

            Files.createDirectories(filePath.getParent());

            Files.write(filePath, file.getBytes());

            String imageUrl = "/fisherman/uploads/" + fileName;
            post.setImageUrl(imageUrl);
            repository.save(post);

            return imageUrl;
        } catch (IOException e) {
            throw new AnErrorOccurredException(communityId.toString());
        }
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
