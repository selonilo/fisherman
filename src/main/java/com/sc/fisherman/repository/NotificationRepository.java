package com.sc.fisherman.repository;

import com.sc.fisherman.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByReceiverUserIdAndIsRead(Long userId, boolean isRead);
    long countByReceiverUserIdAndIsRead(Long userId, boolean isRead);
}
