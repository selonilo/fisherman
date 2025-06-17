package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.model.dto.post.PostModel;

public interface CommentService {
    void update(CommentModel commentModel);
    void save(CommentModel commentModel);
    void delete(Long commentId);
}
