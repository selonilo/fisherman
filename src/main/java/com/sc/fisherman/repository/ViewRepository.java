package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.ViewEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<ViewEntity, Long> {
    Optional<ViewEntity> findByContentTypeAndContentIdAndUserId(EnumContentType contentType, Long contentId, Long userId);
    long countByContentTypeAndContentId(EnumContentType contentType, Long contentId);
}
