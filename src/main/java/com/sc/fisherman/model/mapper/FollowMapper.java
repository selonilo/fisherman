package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.follow.FollowModel;
import com.sc.fisherman.model.entity.FollowEntity;

import java.util.List;

public class FollowMapper {

    public static FollowModel mapTo(FollowEntity entity) {
        FollowModel model = new FollowModel();
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

    public static FollowEntity mapTo(FollowModel model) {
        FollowEntity entity = new FollowEntity();
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

    public static List<FollowModel> mapToList(List<FollowEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(FollowMapper::mapTo).toList();
    }
}
