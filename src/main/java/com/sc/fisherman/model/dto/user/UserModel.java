package com.sc.fisherman.model.dto.user;

import com.sc.fisherman.model.dto.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel {
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String location;
    private String imageUrl;
    private Long postCount;
    private Long followCount;
    private Long followerCount;
    private Long commentCount;
}
