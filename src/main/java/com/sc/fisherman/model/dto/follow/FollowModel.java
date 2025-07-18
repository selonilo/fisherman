package com.sc.fisherman.model.dto.follow;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.enums.EnumContentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowModel extends BaseModel {
    @NotNull
    private Long userId;
    @NotNull
    private Long contentId;
    @NotNull
    private EnumContentType contentType;
}
