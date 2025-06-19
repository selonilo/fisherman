package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.community.CommunityModel;
import com.sc.fisherman.model.dto.sales.SalesModel;
import com.sc.fisherman.service.CommunityService;
import com.sc.fisherman.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    @Autowired
    private SalesService service;

    @Operation(summary = "Yeni bir ilan kaydet (Fotoğraflı veya fotoğrafsız)", description = "Kullanıcıdan alınan verilerle yeni bir ilan oluşturur. " +
            "Görsel içerebilir. Postman kullanımında body kısmında form-data seçilir. Zorunlu alanlar key bölümüne girilerek kayıt yapılır.")
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SalesModel> save(@ModelAttribute SalesModel model) {
        return ResponseEntity.ok(service.save(model));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<SalesModel> update(@RequestBody SalesModel model) {
        return ResponseEntity.ok(service.update(model));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<SalesModel> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")  Long id) {
        service.delete(id);
    }

    @GetMapping("/getList")
    public ResponseEntity<List<SalesModel>> getList() {
        return ResponseEntity.ok(service.getList());
    }



    @Operation(summary = "İlan fotoğraf ekleme servisi", description = "Daha önceden eklenen bir ilana fotoğraf eklemeye yarar.")
    @PostMapping("/uploadImage/{salesId}")
    public void uploadImage(
            @PathVariable Long communityId,
            @RequestParam("file") List<MultipartFile> files) {
        service.uploadImage(communityId, files);
    }

    @Operation(summary = "Fotoğraf silme servisi", description = "İlana eklenen fotoğrafı silme servisi.")
    @DeleteMapping("/deleteImage/{salesId}")
    public void deleteImage(@PathVariable Long salesId) {
        service.deleteImage(salesId);
    }

}
