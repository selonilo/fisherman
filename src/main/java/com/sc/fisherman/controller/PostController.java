package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.TotalStatsModel;
import com.sc.fisherman.model.dto.post.CommentModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.dto.post.PostQueryModel;
import com.sc.fisherman.service.PostService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/save")
    public ResponseEntity<PostModel> save(@RequestBody PostModel postModel) {
        return ResponseEntity.ok(postService.save(postModel));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostModel> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")  Long id) {
        postService.delete(id);
    }

    @PutMapping("/like/{postId}/{userId}")
    public void likePost(@PathVariable(name = "postId") @NotNull Long postId, @PathVariable(name = "userId") @NotNull Long userId) {
        postService.likePost(postId, userId);
    }

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

    @PostMapping("/uploadImage/{postId}")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable Long postId,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = postService.uploadImage(postId, file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteImage/{postId}")
    public void deleteImage(@PathVariable Long postId) {
        postService.deleteImage(postId);
    }

    @GetMapping("/getTotalStats")
    public ResponseEntity<TotalStatsModel> getTotalStats() {
        return ResponseEntity.ok(postService.getTotalStats());
    }

    @PostMapping("/commentPost")
    public void commentPost(@RequestBody CommentModel commentModel) {
        postService.commentPost(commentModel);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public void deleteComment(@PathVariable(name = "commentId") Long commentId) {
        postService.deleteComment(commentId);
    }
}
