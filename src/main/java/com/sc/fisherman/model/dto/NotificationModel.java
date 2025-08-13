package com.sc.fisherman.model.dto;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.enums.EnumNotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel extends BaseModel {
    private Long receiverUserId;
    private Long senderUserId;
    private EnumContentType contentType;
    private Long contentId;
    private EnumNotificationType notificationType;
    private boolean isRead;
    private String message;
    private String userImageUrl;
    private PostModel postModel;
}
