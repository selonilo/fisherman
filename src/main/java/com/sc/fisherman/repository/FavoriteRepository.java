package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.FavoriteEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    Optional<FavoriteEntity> findByContentTypeAndUserIdAndContentId(EnumContentType contentType, Long contentId, Long userId);
    List<FavoriteEntity> findAllByContentTypeAndContentIdIn(EnumContentType contentType, List<Long> contentIdList);
    List<FavoriteEntity> findAllByContentTypeAndContentId(EnumContentType contentType, Long contentId);
    long countByContentTypeAndContentId(EnumContentType contentType, Long contentId);
}
