package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.entity.PostEntity;

import java.util.List;

public class PostMapper {

    public static PostModel mapTo(PostEntity entity) {
        PostModel model = new PostModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setUserId(entity.getUserId());
        model.setFishType(entity.getFishType());
        model.setImageUrl(entity.getImageUrl());
        model.setLocationId(entity.getLocationId());
        model.setCommunityId(entity.getCommunityId());
        return model;
    }

    public static PostEntity mapTo(PostModel model) {
        PostEntity entity = new PostEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setTitle(model.getTitle());
        entity.setContent(model.getContent());
        entity.setUserId(model.getUserId());
        entity.setFishType(model.getFishType());
        entity.setImageUrl(model.getImageUrl());
        entity.setLocationId(model.getLocationId());
        entity.setCommunityId(model.getCommunityId());
        return entity;
    }

    public static List<PostModel> mapToList(List<PostEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(PostMapper::mapTo).toList();
    }
}
