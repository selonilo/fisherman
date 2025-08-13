package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.entity.NotificationEntity;

import java.util.List;

public class NotificationMapper {

    public static NotificationModel mapTo(NotificationEntity entity) {
        NotificationModel model = new NotificationModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setReceiverUserId(entity.getReceiverUserId());
        model.setSenderUserId(entity.getSenderUserId());
        model.setContentType(entity.getContentType());
        model.setContentId(entity.getContentId());
        model.setNotificationType(entity.getNotificationType());
        model.setRead(entity.isRead());
        model.setMessage(entity.getMessage());
        return model;
    }

    public static NotificationEntity mapTo(NotificationModel model) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setReceiverUserId(model.getReceiverUserId());
        entity.setSenderUserId(model.getSenderUserId());
        entity.setContentType(model.getContentType());
        entity.setContentId(model.getContentId());
        entity.setNotificationType(model.getNotificationType());
        entity.setRead(model.isRead());
        entity.setMessage(model.getMessage());
        return entity;
    }

    public static List<NotificationModel> mapToList(List<NotificationEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(NotificationMapper::mapTo).toList();
    }
}
