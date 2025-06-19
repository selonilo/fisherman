package com.sc.fisherman.controller;

import com.sc.fisherman.model.dto.comment.CommentModel;
import com.sc.fisherman.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
