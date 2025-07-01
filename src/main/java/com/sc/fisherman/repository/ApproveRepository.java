package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.ApproveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApproveRepository extends JpaRepository<ApproveEntity, Long> {
    Optional<ApproveEntity> findByLocationIdAndUserId(Long locationId, Long userId);
    List<ApproveEntity> findAllByLocationIdIn(List<Long> locationIdList);
    List<ApproveEntity> findAllByLocationId(Long locationId);
    long countByLocationId(Long locationId);
}
