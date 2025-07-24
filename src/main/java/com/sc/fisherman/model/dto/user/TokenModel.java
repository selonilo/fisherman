package com.sc.fisherman.model.dto.user;

import lombok.Data;

@Data
public class TokenModel {
    private String token;
    private Long userId;
    private String name;
    private String imageUrl;
}
