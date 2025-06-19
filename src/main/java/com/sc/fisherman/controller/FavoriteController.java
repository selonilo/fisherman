package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.model.dto.sales.SalesModel;
import com.sc.fisherman.service.FavoriteService;
import com.sc.fisherman.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
