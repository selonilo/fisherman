package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ENTITY")
public class UserEntity extends BaseEntity {
    @NotBlank
    @Column(name = "NAME",length = 60)
    private String name;

    @NotBlank
    @Column(name = "SURNAME",length = 60)
    private String surname;

    @NotBlank
    @Column(name = "MAIL",length = 60,unique = true)
    private String mail;

    @NotBlank
    @Column(name = "PASSWORD",length = 60)
    private String password;

    @NotBlank
    @Column(name = "LOCATION",length = 60)
    private String location;

    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
