package com.springboot.taskify.service.impl;

import com.springboot.taskify.dto.CommentDTO;
import com.springboot.taskify.exception.ResourceNotFoundException;
import com.springboot.taskify.exception.TaskAPIException;
import com.springboot.taskify.mapper.CommentMapper;
import com.springboot.taskify.model.Comment;
import com.springboot.taskify.model.Task;
import com.springboot.taskify.repository.CommentRepository;
import com.springboot.taskify.repository.TagRepository;
import com.springboot.taskify.repository.TaskRepository;
import com.springboot.taskify.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository, TagRepository tagRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<CommentDTO> getAllComment(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        return task.getComments().stream().map(CommentMapper::toDTO).toList();
    }

    @Override
    public CommentDTO getComment(Long taskId,
                                 Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getTask().getId().equals(taskId)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this Task");
        }
        return CommentMapper.toDTO(comment);
    }

    @Override
    public CommentDTO addComment(Long taskId,
                                 CommentDTO commentDTO) {
        Comment comment = CommentMapper.toEntity(commentDTO);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        comment.setTask(task);
        return CommentMapper.toDTO(commentRepository.save(comment));
    }

    @Override
    public CommentDTO updateComment(Long taskId,
                                    Long commentId,
                                    CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getTask().getId().equals(taskId)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this Task");
        }
        comment.setCommentText(commentDTO.getCommentText());
        return CommentMapper.toDTO(comment);
    }

    @Override
    public void deleteComment(Long taskId,
                              Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getTask().getId().equals(taskId)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this Task");
        }
        commentRepository.delete(comment);
    }
}
