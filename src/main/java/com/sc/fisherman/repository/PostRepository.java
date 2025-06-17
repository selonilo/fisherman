package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.PostEntity;
import com.sc.fisherman.model.enums.EnumFishType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p WHERE (:title IS NULL OR :title = '' OR UPPER(p.title) LIKE UPPER(CONCAT('%', :title, '%')) AND (:fishType IS NULL OR p.fishType = :fishType))")
    Page<PostEntity> findAllPost(@Param("title") String title, @Param("postType") EnumFishType fishType, Pageable pageable);

    List<PostEntity> findAllByUserId(Long userId);

    long countByUserId(Long userId);

    List<PostEntity> findAllByLocationId(Long locationId);
}
