package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService service;

    @GetMapping("/getById/{id}")
    public ResponseEntity<LocationModel> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<LocationModel> save(@RequestBody LocationModel model) {
        return ResponseEntity.ok(service.save(model));
    }

    @PutMapping("/update")
    public ResponseEntity<LocationModel> update(@RequestBody LocationModel model) {
        return ResponseEntity.ok(service.update(model));
    }

    @DeleteMapping("/delete/{id}")
    public void login(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

    @GetMapping("/getList")
    public ResponseEntity<List<LocationModel>> getList() {
        return ResponseEntity.ok(service.getList());
    }

    @GetMapping("/getList/{userId}")
    public ResponseEntity<List<LocationModel>> getList(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getList(userId));
    }

    @PutMapping("/approve/{locationId}/{userId}")
    public ResponseEntity<Boolean> approveLocation(@PathVariable(name = "locationId") @NotNull Long locationId, @PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(service.approveLocation(locationId, userId));
    }

    @PutMapping("/unApprove/{locationId}/{userId}")
    public ResponseEntity<Boolean> unApproveLocation(@PathVariable(name = "locationId") @NotNull Long locationId, @PathVariable(name = "userId") @NotNull Long userId) {
        return ResponseEntity.ok(service.unApproveLocation(locationId, userId));
    }
}
