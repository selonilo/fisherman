package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "COMMUNITY_ENTITY")
public class CommunityEntity extends BaseEntity {
    @NotBlank
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;

    @NotNull
    @Column(name = "POST_CONFIRMATION")
    private Boolean postConfirmation;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
