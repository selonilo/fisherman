package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.LoginModel;
import com.sc.fisherman.model.dto.user.PasswordRefreshModel;
import com.sc.fisherman.model.dto.user.TokenModel;
import com.sc.fisherman.model.dto.user.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthService {
    UserModel getById(Long id);
    UserModel register(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    TokenModel login(LoginModel loginModel);
    ResponseMessageModel refreshPassword(PasswordRefreshModel passwordRefreshModel);
    String uploadUserImage(Long userId, MultipartFile file);
    void deleteUserImage(Long userId);
    String getImage(Long userId);
    void followUser(Long followUserId, Long followerUserId);
    void unFollowUser(Long followUserId, Long followerUserId);
    List<UserModel> getFollowListByUserId(Long userId);
    List<UserModel> getFollowerListByUserId(Long userId);
    List<NotificationModel> getNotification(Long userId);
}
