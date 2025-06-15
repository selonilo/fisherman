package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.LoginModel;
import com.sc.fisherman.model.dto.user.PasswordRefreshModel;
import com.sc.fisherman.model.dto.user.TokenModel;
import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.service.AuthService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(authService.getById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(authService.register(userModel));
    }

    @PutMapping("/update")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(authService.updateUser(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenModel> login(@RequestBody LoginModel loginModel) {
        return ResponseEntity.ok(authService.login(loginModel));
    }

    @PostMapping("/refreshPassword")
    public ResponseEntity<ResponseMessageModel> refreshPassword(@RequestBody PasswordRefreshModel passwordRefreshModel) {
        return ResponseEntity.ok(authService.refreshPassword(passwordRefreshModel));
    }

    @PostMapping("/uploadImage/{userId}")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = authService.uploadUserImage(userId, file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteImage/{userId}")
    public void deleteImage(@PathVariable Long userId) {
        authService.deleteUserImage(userId);
    }


    @GetMapping("getImage/{userId}")
    public ResponseEntity<String> getImage(@PathVariable Long userId) {
        String imageUrl = authService.getImage(userId);
        return ResponseEntity.ok(imageUrl);
    }

    @PutMapping("/followUser/{followUserId}/{followerUserId}")
    public void followUser(@PathVariable(name = "followUserId") @NotNull Long followUserId, @PathVariable(name = "followerUserId") @NotNull Long followerUserId) {
        authService.followUser(followUserId, followerUserId);
    }

    @PutMapping("/unFollowUser/{followUserId}/{followerUserId}")
    public void unFollowUser(@PathVariable(name = "followUserId") @NotNull Long followUserId, @PathVariable(name = "followerUserId") @NotNull Long followerUserId) {
        authService.unFollowUser(followUserId, followerUserId);
    }

    @GetMapping("/getFollowListByUserId/{userId}")
    public ResponseEntity<List<UserModel>> getFollowListByUserId(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getFollowListByUserId(userId));
    }

    @GetMapping("/getFollowerListByUserId/{userId}")
    public ResponseEntity<List<UserModel>> getFollowerListByUserId(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getFollowerListByUserId(userId));
    }

    @GetMapping("/getNotification/{userId}")
    public ResponseEntity<List<NotificationModel>> getNotification(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getNotification(userId));
    }
}
