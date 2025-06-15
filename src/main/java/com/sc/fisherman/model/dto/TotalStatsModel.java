package com.sc.fisherman.model.dto;

import com.sc.fisherman.model.dto.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalStatsModel extends BaseModel {
    private Long totalPostCount;
    private Long totalUserCount;
    private Long totalLikeCount;
    private Long totalCommentCount;
}
