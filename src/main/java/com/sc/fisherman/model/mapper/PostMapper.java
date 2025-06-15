package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.entity.PostEntity;

import java.util.List;

public class PostMapper {

    public static PostModel mapTo(PostEntity postEntity) {
        PostModel postModel = new PostModel();
        postModel.setId(postEntity.getId());
        postModel.setUpdatedDate(postEntity.getUpdatedDate());
        postModel.setCreatedDate(postEntity.getCreatedDate());
        postModel.setUpdatedBy(postEntity.getUpdatedBy());
        postModel.setCreatedBy(postEntity.getCreatedBy());
        postModel.setTitle(postEntity.getTitle());
        postModel.setContent(postEntity.getContent());
        postModel.setUserId(postEntity.getUserId());
        postModel.setPostType(postEntity.getPostType());
        postModel.setImageUrl(postEntity.getImageUrl());
        return postModel;
    }

    public static PostEntity mapTo(PostModel postModel) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postModel.getId());
        postEntity.setUpdatedDate(postModel.getUpdatedDate());
        postEntity.setCreatedDate(postModel.getCreatedDate());
        postEntity.setUpdatedBy(postModel.getUpdatedBy());
        postEntity.setCreatedBy(postModel.getCreatedBy());
        postEntity.setTitle(postModel.getTitle());
        postEntity.setContent(postModel.getContent());
        postEntity.setUserId(postModel.getUserId());
        postEntity.setPostType(postModel.getPostType());
        postEntity.setImageUrl(postModel.getImageUrl());
        return postEntity;
    }

    public static List<PostModel> mapToList(List<PostEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(PostMapper::mapTo).toList();
    }
}
