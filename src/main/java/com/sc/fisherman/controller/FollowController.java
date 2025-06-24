package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.model.dto.follow.FollowModel;
import com.sc.fisherman.service.FavoriteService;
import com.sc.fisherman.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService service;

    @Operation(summary = "Takip servisi", description = "Takip servisi")
    @PostMapping("/follow")
    public ResponseEntity<Boolean> favorite(@RequestBody FollowModel model) {
        return ResponseEntity.ok(service.follow(model));
    }

    @Operation(summary = "Takip çıkarma", description = "Takip çıkarma")
    @PostMapping("/unFollow")
    public ResponseEntity<Boolean> unFavorite(@RequestBody FollowModel model) {
        return ResponseEntity.ok(service.unFollow(model));
    }

}
