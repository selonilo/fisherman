package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.service.LocationService;
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
}
