package com.springboot.taskify.service;

import com.springboot.taskify.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComment(Long taskId);

    CommentDTO getComment(Long taskId, Long commentId);

    CommentDTO addComment(Long taskId, CommentDTO commentDTO);

    CommentDTO updateComment(Long taskId, Long commentId, CommentDTO commentDTO);

    void deleteComment(Long taskId, Long commentId);
}
