package com.springboot.taskify.controller;

import com.springboot.taskify.dto.CommentDTO;
import com.springboot.taskify.service.CommentService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllComment(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getAllComment(taskId));
    }

    @GetMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long taskId,
                                                     @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getComment(taskId, commentId));
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long taskId,
                                                 @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.addComment(taskId, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(taskId, commentId, commentDTO));
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<Null> deleteComment(@PathVariable Long taskId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(taskId, commentId);
        return ResponseEntity.noContent().build();
    }


}
