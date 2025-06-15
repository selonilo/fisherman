package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowUserIdAndFollowerUserId(Long followUserId, Long followerUserId);
    List<FollowEntity> findByFollowUserId(Long followUserId);
    List<FollowEntity> findByFollowerUserId(Long followerUserId);
    long countByFollowUserId(Long followUserId);
    long countByFollowerUserId(Long followerUserId);
}
