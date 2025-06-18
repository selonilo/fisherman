package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.community.CommunityModel;
import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.model.entity.CommunityEntity;
import com.sc.fisherman.model.entity.LocationEntity;

import java.util.List;

public class CommunityMapper {

    public static CommunityModel mapTo(CommunityEntity entity) {
        CommunityModel model = new CommunityModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setIsPublic(entity.getIsPublic());
        model.setPostConfirmation(entity.getPostConfirmation());
        model.setUserId(entity.getUserId());
        model.setImageUrl(entity.getImageUrl());
        return model;
    }

    public static CommunityEntity mapTo(CommunityModel model) {
        CommunityEntity entity = new CommunityEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setIsPublic(model.getIsPublic());
        entity.setPostConfirmation(model.getPostConfirmation());
        entity.setUserId(model.getUserId());
        entity.setImageUrl(model.getImageUrl());
        return entity;
    }

    public static List<CommunityModel> mapToList(List<CommunityEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(CommunityMapper::mapTo).toList();
    }
}
