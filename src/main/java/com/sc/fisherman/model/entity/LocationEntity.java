package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import com.sc.fisherman.model.enums.EnumFishType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FISH_TYPE_LIST")
    private List<EnumFishType> fishTypeList;

    @Column(name = "COORDINATE")
    private String coordinate;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;
}
