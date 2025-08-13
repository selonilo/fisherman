package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.enums.EnumNotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTIFICATION_ENTITY")
public class NotificationEntity extends BaseEntity {

    @NotNull
    @Column(name = "RECEIVER_USER_ID") // Bildirimi alan kullanıcı (kime gidiyor)
    private Long receiverUserId;

    @NotNull
    @Column(name = "SENDER_USER_ID")  // Bildirimi tetikleyen kullanıcı (kim yaptı)
    private Long senderUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CONTENT_TYPE", length = 30)  // Hangi içerik ile ilgili (POST, COMMENT, FOLLOW, vb)
    private EnumContentType contentType;

    @NotNull
    @Column(name = "CONTENT_ID")  // İlgili içerik (örneğin postId, commentId)
    private Long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOTIFICATION_TYPE", length = 30)
    private EnumNotificationType notificationType;

    @Column(name = "IS_READ", nullable = false)
    private boolean isRead = false;  // Bildirim okundu mu?

    @Column(name = "MESSAGE", length = 255)
    // Opsiyonel: Bildirim mesajı veya açıklaması
    private String message;
}
