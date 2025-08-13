package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.follow.FollowModel;
import com.sc.fisherman.model.entity.NotificationEntity;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.enums.EnumNotificationType;
import com.sc.fisherman.model.mapper.FollowMapper;
import com.sc.fisherman.repository.FollowRepository;
import com.sc.fisherman.repository.NotificationRepository;
import com.sc.fisherman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository repository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Boolean follow(FollowModel model) {
        var optUser = userRepository.findById(model.getUserId());
        if (optUser.isPresent()) {
            repository.saveAndFlush(FollowMapper.mapTo(model));
            NotificationEntity notif = new NotificationEntity();
            notif.setReceiverUserId(model.getContentId());
            notif.setSenderUserId(model.getUserId());
            notif.setContentType(EnumContentType.USER);
            notif.setContentId(model.getUserId());
            notif.setNotificationType(EnumNotificationType.FOLLOW);
            notif.setMessage(optUser.get().getName() + " isimli kullanıcı sizi takip etti.");
            notificationRepository.save(notif);
        }
        return true;
    }

    public Boolean unFollow(FollowModel model) {
        var optFavorite = repository.findByContentTypeAndUserIdAndContentId(model.getContentType(), model.getUserId(), model.getContentId());
        optFavorite.ifPresent(favorite -> repository.delete(favorite));
        return false;
    }
}
