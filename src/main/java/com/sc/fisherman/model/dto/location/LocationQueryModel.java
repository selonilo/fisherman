package com.sc.fisherman.model.dto.location;

import com.sc.fisherman.model.enums.EnumFishType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationQueryModel {
    private String name;
    private List<EnumFishType> fishTypeList;
    private String address;
    private Long userId;
    private Boolean onlyFavorite;
}
