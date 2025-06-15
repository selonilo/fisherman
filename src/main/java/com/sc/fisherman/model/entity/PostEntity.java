package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.enums.EnumPostType;
import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.*;
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
    @Column(name = "TITLE",length = 60)
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POST_TYPE")
    private EnumPostType postType;

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
