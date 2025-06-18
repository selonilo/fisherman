package com.sc.fisherman.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRefreshModel {
    @NotBlank
    private String mail;
}
