package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.model.entity.FavoriteEntity;

import java.util.List;

public class FavoriteMapper {

    public static FavoriteModel mapTo(FavoriteEntity entity) {
        FavoriteModel model = new FavoriteModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUserId(entity.getUserId());
        model.setContentId(entity.getContentId());
        model.setContentType(entity.getContentType());
        return model;
    }

    public static FavoriteEntity mapTo(FavoriteModel model) {
        FavoriteEntity entity = new FavoriteEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setUserId(model.getUserId());
        entity.setContentId(model.getContentId());
        entity.setContentType(model.getContentType());
        return entity;
    }

    public static List<FavoriteModel> mapToList(List<FavoriteEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(FavoriteMapper::mapTo).toList();
    }
}
