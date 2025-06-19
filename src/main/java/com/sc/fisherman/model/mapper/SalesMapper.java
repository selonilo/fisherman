package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.sales.SalesModel;
import com.sc.fisherman.model.entity.SalesEntity;

import java.util.List;

public class SalesMapper {

    public static SalesModel mapTo(SalesEntity entity) {
        SalesModel model = new SalesModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setPrice(entity.getPrice());
        model.setSalesType(entity.getSalesType());
        model.setImageUrlList(entity.getImageUrlList());
        model.setUserId(entity.getUserId());
        return model;
    }

    public static SalesEntity mapTo(SalesModel model) {
        SalesEntity entity = new SalesEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setPrice(model.getPrice());
        entity.setSalesType(model.getSalesType());
        entity.setImageUrlList(model.getImageUrlList());
        entity.setUserId(model.getUserId());
        return entity;
    }

    public static List<SalesModel> mapToList(List<SalesEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(SalesMapper::mapTo).toList();
    }
}
