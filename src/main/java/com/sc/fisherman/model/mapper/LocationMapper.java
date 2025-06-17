package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.model.entity.LocationEntity;

import java.util.List;

public class LocationMapper {

    public static LocationModel mapTo(LocationEntity entity) {
        LocationModel model = new LocationModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setCoordinate(entity.getCoordinate());
        model.setUserId(entity.getUserId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setFishTypeList(entity.getFishTypeList());
        model.setAddress(entity.getAddress());
        model.setIsPublic(entity.getIsPublic());
        return model;
    }

    public static LocationEntity mapTo(LocationModel model) {
        LocationEntity entity = new LocationEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCoordinate(model.getCoordinate());
        entity.setUserId(model.getUserId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setFishTypeList(model.getFishTypeList());
        entity.setAddress(model.getAddress());
        entity.setIsPublic(model.getIsPublic());
        return entity;
    }

    public static List<LocationModel> mapToList(List<LocationEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(LocationMapper::mapTo).toList();
    }
}
