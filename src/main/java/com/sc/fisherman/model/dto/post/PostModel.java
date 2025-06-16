package com.sc.fisherman.model.dto.post;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.enums.EnumFishType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel extends BaseModel {
    @Size(min = 5)
    @NotBlank
    private String title;
    @Size(min = 5)
    @NotBlank
    private String content;
    @NotNull
    @Positive
    private Long userId;
    private Long locationId;
    private Long viewNumber = 0L;
    private Long likeNumber = 0L;
    @NotBlank
    private EnumFishType fishType;
    private String imageUrl;
    private String userImageUrl;
    private String name;
    private Boolean isLiked;
    private Boolean isFollowed;
    private List<CommentModel> commentModelList;
}
