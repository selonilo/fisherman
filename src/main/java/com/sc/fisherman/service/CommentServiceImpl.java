package com.sc.fisherman.service;

import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.TotalStatsModel;
import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.dto.post.PostQueryModel;
import com.sc.fisherman.model.entity.CommentEntity;
import com.sc.fisherman.model.entity.LikeEntity;
import com.sc.fisherman.model.entity.PostEntity;
import com.sc.fisherman.model.entity.ViewEntity;
import com.sc.fisherman.model.mapper.CommentMapper;
import com.sc.fisherman.model.mapper.PostMapper;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
