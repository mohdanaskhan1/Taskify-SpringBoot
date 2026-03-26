package com.springboot.taskify.mapper;

import com.springboot.taskify.dto.CommentDTO;
import com.springboot.taskify.model.Comment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toEntity(CommentDTO commentDTO) {
        if (commentDTO == null) return null;
        return Comment.builder()
                .commentText(commentDTO.getCommentText())
                .build();
    }

    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;
        return CommentDTO.builder()
                .id(comment.getId())
                .commentText(comment.getCommentText())
                .build();
    }
}
