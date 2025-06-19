package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.comment.CommentModel;

public interface CommentService {
    void update(CommentModel commentModel);
    void save(CommentModel commentModel);
    void delete(Long commentId);
}
