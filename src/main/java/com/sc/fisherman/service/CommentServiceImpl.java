package com.sc.fisherman.service;

import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.model.mapper.CommentMapper;
import com.sc.fisherman.repository.CommentRepository;
import com.sc.fisherman.repository.PostRepository;
import com.sc.fisherman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(CommentModel commentModel) {
        var optPost = postRepository.findById(commentModel.getPostId());
        var optUser = userRepository.findById(commentModel.getUserId());
        if (optPost.isPresent() && optUser.isPresent()) {
            repository.saveAndFlush(CommentMapper.mapTo(commentModel));
        } else {
            throw new NotFoundException(commentModel.getPostId().toString().concat(commentModel.getUserId().toString()));
        }
    }

    public void update(CommentModel commentModel) {
        var optEntity = repository.findById(commentModel.getId());
        if (optEntity.isPresent()) {
            optEntity.get().setComment(commentModel.getComment());
            repository.saveAndFlush(optEntity.get());
        } else {
            throw new NotFoundException(commentModel.getPostId().toString().concat(commentModel.getUserId().toString()));
        }
    }

    public void delete(Long commentId) {
        var optEntity = repository.findById(commentId);
        if (optEntity.isPresent()) {
            optEntity.ifPresent(commentEntity -> repository.delete(commentEntity));
        } else {
            throw new NotFoundException(commentId.toString());
        }
    }
}
