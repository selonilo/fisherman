package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FOLLOW_ENTITY", uniqueConstraints = @UniqueConstraint(columnNames = {"FOLLOWER_USER_ID", "FOLLOW_USER_ID"}))
public class FollowEntity extends BaseEntity {

    @Column(name = "FOLLOWER_USER_ID")
    private Long followerUserId;

    @Column(name = "FOLLOW_USER_ID")
    private Long followUserId;
}
