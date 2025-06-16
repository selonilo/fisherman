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
        return entity;
    }

    public static List<LocationModel> mapToList(List<LocationEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(LocationMapper::mapTo).toList();
    }
}
