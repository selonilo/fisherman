package com.sc.fisherman.model.mapper;

import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.model.entity.CommentEntity;

import java.util.List;

public class CommentMapper {

    public static CommentModel mapTo(CommentEntity entity) {
        CommentModel model = new CommentModel();
        model.setId(entity.getId());
        model.setUpdatedDate(entity.getUpdatedDate());
        model.setCreatedDate(entity.getCreatedDate());
        model.setUpdatedBy(entity.getUpdatedBy());
        model.setCreatedBy(entity.getCreatedBy());
        model.setUserId(entity.getUserId());
        model.setParentCommentId(entity.getParentCommentId());
        model.setComment(entity.getComment());
        model.setPostId(entity.getPostId());
        return model;
    }

    public static CommentEntity mapTo(CommentModel model) {
        CommentEntity entity = new CommentEntity();
        entity.setId(model.getId());
        entity.setUpdatedDate(model.getUpdatedDate());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setUpdatedBy(model.getUpdatedBy());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setUserId(model.getUserId());
        entity.setParentCommentId(model.getParentCommentId());
        entity.setComment(model.getComment());
        entity.setPostId(model.getPostId());
        return entity;
    }

    public static List<CommentModel> mapToList(List<CommentEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(CommentMapper::mapTo).toList();
    }
}
