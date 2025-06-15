package com.sc.fisherman.model.entity.base;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreatedDate(LocalDate.now());
        entity.setUpdatedDate(LocalDate.now());
        entity.setCreatedBy(getCurrentUser());
        entity.setUpdatedBy(getCurrentUser());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedDate(LocalDate.now());
        entity.setUpdatedBy(getCurrentUser());
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
