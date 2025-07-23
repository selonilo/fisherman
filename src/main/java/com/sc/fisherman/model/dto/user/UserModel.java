package com.sc.fisherman.model.dto.user;

import com.sc.fisherman.model.dto.base.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String mail;
    @NotBlank
    private String password;
    @NotBlank
    private String location;
    private String imageUrl;
    private Long postCount;
    private Long followCount;
    private Long followerCount;
    private Long commentCount;
    private Boolean isFollowed;
}
