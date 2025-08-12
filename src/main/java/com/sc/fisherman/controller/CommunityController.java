package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.TotalStatsModel;
import com.sc.fisherman.model.dto.community.CommunityModel;
import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.dto.post.PostQueryModel;
import com.sc.fisherman.service.CommunityService;
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
@RequestMapping("/api/community")
public class CommunityController {
    @Autowired
    private CommunityService service;

    @Operation(summary = "Yeni bir topluluk kaydet (Fotoğraflı veya fotoğrafsız)", description = "Kullanıcıdan alınan verilerle yeni bir topluluk oluşturur. " +
            "Görsel içerebilir. Postman kullanımında body kısmında form-data seçilir. Zorunlu alanlar key bölümüne girilerek kayıt yapılır.")
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommunityModel> save(@ModelAttribute CommunityModel postModel) {
        return ResponseEntity.ok(service.save(postModel));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommunityModel> update(@ModelAttribute CommunityModel postModel) {
        return ResponseEntity.ok(service.update(postModel));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CommunityModel> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")  Long id) {
        service.delete(id);
    }

    @GetMapping("/getList/{userId}")
    public ResponseEntity<List<CommunityModel>> getList(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getList(userId));
    }

    @GetMapping("/getListByFollowed/{userId}")
    public ResponseEntity<List<CommunityModel>> getListByFollowed(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getListByFollowed(userId));
    }

    @Operation(summary = "Topluluk fotoğraf ekleme servisi", description = "Daha önceden eklenen bir topluluğa fotoğraf eklemeye yarar.")
    @PostMapping("/uploadImage/{communityId}")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable Long communityId,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = service.uploadImage(communityId, file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fotoğraf silme servisi", description = "Topluluğa eklenen fotoğrafı silme servisi.")
    @DeleteMapping("/deleteImage/{communityId}")
    public void deleteImage(@PathVariable Long communityId) {
        service.deleteImage(communityId);
    }

}
