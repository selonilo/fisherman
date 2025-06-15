package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByPostIdAndUserId(Long postId, Long userId);
    List<LikeEntity> findAllByPostIdIn(List<Long> postIdList);
    long countByPostId(Long postId);
}
