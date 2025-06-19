package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService service;

    @Operation(summary = "Favori servisi", description = "Favori servisi")
    @PostMapping("/favorite")
    public ResponseEntity<Boolean> favorite(@RequestBody FavoriteModel model) {
        return ResponseEntity.ok(service.favorite(model));
    }

    @Operation(summary = "Favoriden çıkarma", description = "Favoriden çıkarma")
    @PostMapping("/unFavorite")
    public ResponseEntity<Boolean> unFavorite(@RequestBody FavoriteModel model) {
        return ResponseEntity.ok(service.unFavorite(model));
    }

}
