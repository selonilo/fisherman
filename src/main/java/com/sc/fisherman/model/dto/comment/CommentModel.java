package com.sc.fisherman.model.dto.comment;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel extends BaseModel {
    @NotNull
    private Long postId;
    @NotNull
    private Long userId;
    @NotBlank
    private String comment;
    private Long parentCommentId;
    private UserModel userModel;
}
