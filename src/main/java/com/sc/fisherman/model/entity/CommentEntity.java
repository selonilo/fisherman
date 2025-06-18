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
@Table(name = "COMMENT_ENTITY")
public class CommentEntity extends BaseEntity {
    @NotNull
    @Column(name = "POST_ID")
    private Long postId;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @NotBlank
    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "PARENT_COMMENT_ID")
    private Long parentCommentId;
}
