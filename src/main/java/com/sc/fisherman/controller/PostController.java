package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.TotalStatsModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.dto.post.PostQueryModel;
import com.sc.fisherman.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Yeni bir post kaydet (Fotoğraflı veya fotoğrafsız)", description = "Kullanıcıdan alınan verilerle yeni bir gönderi (post) oluşturur. " +
            "Görsel içerebilir. Postman kullanımında body kısmında form-data seçilir. Zorunlu alanlar key bölümüne girilerek kayıt yapılır.")
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostModel> save(@ModelAttribute PostModel postModel) {
        return ResponseEntity.ok(postService.save(postModel));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<PostModel> update(@RequestBody PostModel postModel) {
        return ResponseEntity.ok(postService.update(postModel));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostModel> getById(@Parameter(description = "PostId") @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")  Long id) {
        postService.delete(id);
    }

    @Operation(summary = "Post beğenme servisi", description = "Post beğenme servisi.")
    @PutMapping("/like/{postId}/{userId}")
    public void likePost(@PathVariable(name = "postId") @NotNull Long postId, @PathVariable(name = "userId") @NotNull Long userId) {
        postService.likePost(postId, userId);
    }

    @Operation(summary = "Post beğeni kaldırma servisi", description = "Post beğeni kaldırma servisi.")
    @PutMapping("/unLike/{postId}/{userId}")
    public void unLikePost(@PathVariable(name = "postId") @NotNull Long postId, @PathVariable(name = "userId") @NotNull Long userId) {
        postService.unLikePost(postId, userId);
    }

    @GetMapping("/getList/{userId}")
    public ResponseEntity<List<PostModel>> getList(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(postService.getList(userId));
    }

    @GetMapping("/getListByUserId/{userId}")
    public ResponseEntity<List<PostModel>> getListByUserId(@PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(postService.getListByUserId(userId));
    }

    @GetMapping("/getListByLocationId/{locationId}/{userId}")
    public ResponseEntity<List<PostModel>> getListByLocationId(@PathVariable(name = "locationId") @NotNull Long locationId,@Parameter(description = "loginUserId") @PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(postService.getListByLocationId(locationId, userId));
    }

    @GetMapping("/getListByCommunityId/{locationId}/{userId}")
    public ResponseEntity<List<PostModel>> getListByCommunityId(@PathVariable(name = "communityId") @NotNull Long communityId,@Parameter(description = "loginUserId") @PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(postService.getListByCommunityId(communityId, userId));
    }

    @GetMapping("/getListByUserIdAndLoginUserId/{userId}/{loginUserId}")
    public ResponseEntity<List<PostModel>> getListByUserIdAndLoginUserId(@PathVariable(name = "userId") @NotNull Long userId, @PathVariable(name = "loginUserId") @NotNull Long loginUserId) {
        return ResponseEntity.ok(postService.getListByUserIdAndLoginUserId(userId, loginUserId));
    }

    @PostMapping("/queryPage")
    public ResponseEntity<Page<PostModel>> findPostWithPagination(Pageable pageable, @RequestBody PostQueryModel queryModel) {
        return ResponseEntity.ok(postService.findPostWithPagination(pageable, queryModel));
    }

    @GetMapping("/isLiked/{postId}/{userId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable(name = "postId") @NotNull Long postId, @PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(postService.isLiked(postId, userId));
    }

    @Operation(summary = "Post fotoğraf ekleme servisi", description = "Daha önceden eklenen bir post a fotoğraf eklemeye yarar.")
    @PostMapping("/uploadImage/{postId}")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable Long postId,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = postService.uploadImage(postId, file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fotoğraf silme servisi", description = "Post a eklenen fotoğrafı silme servisi.")
    @DeleteMapping("/deleteImage/{postId}")
    public void deleteImage(@PathVariable Long postId) {
        postService.deleteImage(postId);
    }

    @Operation(summary = "Toplam istatistik servisi", description = "Toplam post like comment user bilgilerini döner.")
    @GetMapping("/getTotalStats")
    public ResponseEntity<TotalStatsModel> getTotalStats() {
        return ResponseEntity.ok(postService.getTotalStats());
    }
}
