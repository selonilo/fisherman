package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.service.CommentService;
import com.sc.fisherman.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    @PostMapping("/save")
    public void save(@RequestBody CommentModel commentModel) {
        service.save(commentModel);
    }

    @PutMapping("/update")
    public void update(@RequestBody CommentModel commentModel) {
        service.update(commentModel);
    }

    @DeleteMapping("/delete/{commentId}")
    public void delete(@PathVariable(name = "commentId") Long commentId) {
        service.delete(commentId);
    }
}
