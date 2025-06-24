package com.sc.fisherman.model.dto.community;

import com.sc.fisherman.model.dto.base.BaseModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityModel extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean isPublic;
    @NotNull
    private Boolean postConfirmation;
    @NotNull
    private Long userId;
    private Long followedCount;
    private String imageUrl;
    private MultipartFile file;
    private Boolean isFollowed;
}
