package com.sc.fisherman.model.dto;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel extends BaseModel {
    private String icon;
    private String notificationMessage;
    private LocalDate notificationDate;
    private UserModel userModel;
}
