package com.sc.fisherman.model.dto.location;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
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
public class LocationModel extends BaseModel {
    private String name;
    private String description;
    private List<EnumFishType> fishTypeList;
    private String coordinate;
    private String address;
    private Long userId;
    private Boolean isPublic;
    private UserModel userModel;
    private Long approveCount;
    private Boolean isApproved;
}
