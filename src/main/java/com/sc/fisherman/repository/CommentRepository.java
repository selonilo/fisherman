package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long postId);
    List<CommentEntity> findByPostIdAndUserId(Long postId, Long userId);
    List<CommentEntity> findAllByPostIdIn(List<Long> postIdList);
    long countByUserId(Long userId);
}
