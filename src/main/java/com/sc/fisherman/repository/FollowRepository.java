package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.FollowEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByContentTypeAndUserIdAndContentId(EnumContentType contentType, Long followUserId, Long followerUserId);
    List<FollowEntity> findAllByContentTypeAndUserId(EnumContentType contentType, Long userId);
    List<FollowEntity> findAllByContentTypeAndContentId(EnumContentType contentType, Long contentId);
    long countByContentTypeAndUserId(EnumContentType contentType, Long userId);
    long countByContentTypeAndContentId(EnumContentType contentType, Long contentId);
}
