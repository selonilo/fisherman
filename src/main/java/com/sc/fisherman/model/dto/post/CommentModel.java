package com.sc.fisherman.model.dto.post;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel extends BaseModel {
    private Long postId;
    private Long userId;
    private String comment;
    private UserModel userModel;
}
