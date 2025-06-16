package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.model.entity.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserModel mapTo(UserEntity entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setName(entity.getName());
        model.setSurname(entity.getSurname());
        model.setMail(entity.getMail());
        model.setPassword(entity.getPassword());
        model.setLocation(entity.getLocation());
        model.setImageUrl(entity.getImageUrl());
        return model;
    }

    public static UserEntity mapTo(UserModel model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setName(model.getName());
        entity.setSurname(model.getSurname());
        entity.setMail(model.getMail());
        entity.setPassword(model.getPassword());
        entity.setLocation(model.getLocation());
        entity.setImageUrl(model.getImageUrl());
        return entity;
    }

    public static List<UserModel> mapToList(List<UserEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(UserMapper::mapTo).toList();
    }
}
