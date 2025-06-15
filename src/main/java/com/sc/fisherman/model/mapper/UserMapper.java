package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.model.entity.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserModel mapTo(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUpdatedDate(userEntity.getUpdatedDate());
        userModel.setCreatedDate(userEntity.getCreatedDate());
        userModel.setUpdatedBy(userEntity.getUpdatedBy());
        userModel.setCreatedBy(userEntity.getCreatedBy());
        userModel.setName(userEntity.getName());
        userModel.setSurname(userEntity.getSurname());
        userModel.setMail(userEntity.getMail());
        userModel.setPassword(userEntity.getPassword());
        userModel.setLocation(userEntity.getLocation());
        userModel.setImageUrl(userEntity.getImageUrl());
        return userModel;
    }

    public static UserEntity mapTo(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUpdatedDate(userModel.getUpdatedDate());
        userEntity.setCreatedDate(userModel.getCreatedDate());
        userEntity.setUpdatedBy(userModel.getUpdatedBy());
        userEntity.setCreatedBy(userModel.getCreatedBy());
        userEntity.setName(userModel.getName());
        userEntity.setSurname(userModel.getSurname());
        userEntity.setMail(userModel.getMail());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setLocation(userModel.getLocation());
        userEntity.setImageUrl(userModel.getImageUrl());
        return userEntity;
    }

    public static List<UserModel> mapToList(List<UserEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(UserMapper::mapTo).toList();
    }
}
