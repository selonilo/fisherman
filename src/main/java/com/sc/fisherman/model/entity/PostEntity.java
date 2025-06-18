package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.enums.EnumFishType;
import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "POST_ENTITY")
public class PostEntity extends BaseEntity {
    @NotBlank
    @Column(name = "TITLE",length = 60)
    private String title;

    @NotBlank
    @Column(name = "CONTENT")
    private String content;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Column(name = "FISH_TYPE")
    private EnumFishType fishType;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "COMMUNITY_ID")
    private Long communityId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
