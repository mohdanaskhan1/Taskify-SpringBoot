package com.springboot.taskify.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Instant dueDate;
    private String priority;
    private String status;
    private List<CommentDTO> comments;
    private Long userId;
    private List<String> tags;
}
