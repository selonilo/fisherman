package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.NotificationCountModel;
import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthService {
    UserModel getById(Long id);
    UserModel getByIdAndLoginUserId(Long id, Long loginUserId);
    UserModel register(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    TokenModel login(LoginModel loginModel);
    ResponseMessageModel refreshPassword(PasswordRefreshModel passwordRefreshModel);
    String uploadUserImage(Long userId, MultipartFile file);
    void deleteUserImage(Long userId);
    String getImage(Long userId);
    List<NotificationModel> getNotification(Long userId);
    NotificationCountModel getNotificationCount(Long userId);
    List<UserModel> getFollowListByUserId(Long userId);
    List<UserModel> getFollowerListByUserId(Long userId);
    List<UserModel> findWithName(UserQueryModel queryModel);
}
