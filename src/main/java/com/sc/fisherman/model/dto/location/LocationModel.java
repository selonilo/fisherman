package com.sc.fisherman.model.dto.location;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.model.enums.EnumFishType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private List<EnumFishType> fishTypeList;
    @NotBlank
    private String coordinate;
    @NotBlank
    private String address;
    @NotNull
    private Long userId;
    @NotNull
    private Boolean isPublic;
    private UserModel userModel;
    private Long approveCount;
    private Boolean isApproved;
}
