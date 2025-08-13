package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.NotificationCountModel;
import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.*;
import com.sc.fisherman.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(authService.getById(id));
    }

    @GetMapping("/getByIdAndLoginUserId/{id}/{loginUserId}")
    public ResponseEntity<UserModel> getByIdAndLoginUserId(@PathVariable(name = "id") @NotNull Long id, @PathVariable(name = "loginUserId") @NotNull Long loginUserId) {
        return ResponseEntity.ok(authService.getByIdAndLoginUserId(id, loginUserId));
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

    @Operation(summary = "Şifre yenileme servisi", description = "Kullanıcı şifresini unuttuğunda mail adresine şifre göndermeye yarar.")
    @PostMapping("/refreshPassword")
    public ResponseEntity<ResponseMessageModel> refreshPassword(@RequestBody PasswordRefreshModel passwordRefreshModel) {
        return ResponseEntity.ok(authService.refreshPassword(passwordRefreshModel));
    }

    @Operation(summary = "Profil fotoğrafı ekler", description = "Kullanıcı profil fotoğrafı ekler")
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


    @Operation(summary = "Kullanıcının fotoğrafını çekme servisi", description = "Kullanıcının fotoğrafını çeker.")
    @GetMapping("getImage/{userId}")
    public ResponseEntity<String> getImage(@PathVariable Long userId) {
        String imageUrl = authService.getImage(userId);
        return ResponseEntity.ok(imageUrl);
    }

    @Operation(summary = "Bildirim çekme servisi", description = "Giriş yapan kullanıcının postlarına gelen beğeni ve yorumları listeler." +
            " Takip ettiği kullanıcılar yeni post paylaşırsa onlar da listelenir.")
    @GetMapping("/getNotification/{userId}")
    public ResponseEntity<List<NotificationModel>> getNotification(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getNotification(userId));
    }

    @GetMapping("/getNotificationCount/{userId}")
    public ResponseEntity<NotificationCountModel> getNotificationCount(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getNotificationCount(userId));
    }

    @Operation(summary = "Takipçi listesini döner", description = "Kullanıcıyı takip eden kullanıcıların listesini döner.")
    @GetMapping("/getFollowListByUserId/{userId}")
    public ResponseEntity<List<UserModel>> getFollowListByUserId(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getFollowListByUserId(userId));
    }

    @Operation(summary = "Takip edilen kullanıcı listesini döner", description = "Kullanıcının takip ettiği kullanıcıların listesini döner.")
    @GetMapping("/getFollowerListByUserId/{userId}")
    public ResponseEntity<List<UserModel>> getFollowerListByUserId(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(authService.getFollowerListByUserId(userId));
    }

    @PostMapping("/queryList")
    public ResponseEntity<List<UserModel>> findWithName(@RequestBody UserQueryModel queryModel) {
        return ResponseEntity.ok(authService.findWithName(queryModel));
    }
}
