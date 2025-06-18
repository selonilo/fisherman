package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.CommunityUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityUserRepository extends JpaRepository<CommunityUserEntity, Long> {
    List<CommunityUserEntity> findAllByUserId(Long userId);
    Optional<CommunityUserEntity> findByCommunityIdAndUserId(Long communityId, Long userId);
    long countByCommunityId(Long communityId);
}
