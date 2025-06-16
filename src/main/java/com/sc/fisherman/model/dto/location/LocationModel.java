package com.sc.fisherman.model.dto.location;

import com.sc.fisherman.model.dto.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel extends BaseModel {
    private String coordinate;
    private Long userId;
}
