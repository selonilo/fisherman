package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    List<CommunityEntity> findAllByIdIn(List<Long> id);

    Optional<CommunityEntity> findByName(String name);
}
