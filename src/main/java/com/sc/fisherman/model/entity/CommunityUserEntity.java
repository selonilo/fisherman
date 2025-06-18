package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMUNITY_USER_ENTITY", uniqueConstraints = @UniqueConstraint(columnNames = {"COMMUNITY_ID", "USER_ID"}))
public class CommunityUserEntity extends BaseEntity {
    @NotNull
    @Column(name = "COMMUNITY_ID")
    private Long communityId;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;
}
