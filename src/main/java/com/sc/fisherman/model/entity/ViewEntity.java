package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import jakarta.persistence.*;
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
@Table(name = "VIEW_ENTITY", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "CONTENT_TYPE", "CONTENT_ID"}))
public class ViewEntity extends BaseEntity {

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CONTENT_TYPE", length = 30)
    private EnumContentType contentType;
}
