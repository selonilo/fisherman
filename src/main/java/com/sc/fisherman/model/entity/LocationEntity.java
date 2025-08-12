package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import com.sc.fisherman.model.enums.EnumFishType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOCATION_ENTITY")
public class LocationEntity extends BaseEntity {
    @NotBlank
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "FISH_TYPE_LIST")
    private List<EnumFishType> fishTypeList;

    @NotBlank
    @Column(name = "COORDINATE")
    private String coordinate;

    @Column(name = "ADDRESS")
    private String address;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;
}
