package com.springboot.taskify.mapper;

import com.springboot.taskify.dto.TaskDTO;
import com.springboot.taskify.model.Tag;
import com.springboot.taskify.model.Task;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskMapper {
    public static Task toTask(TaskDTO taskDTO) {
        if (taskDTO == null) return null;
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dueDate(taskDTO.getDueDate())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .build();
    }

    public static TaskDTO toDto(Task task) {
        if (task == null) return null;
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .comments(task.getComments() != null
                        ? task.getComments().stream()
                        .map(CommentMapper::toDTO)
                        .toList()
                        : null)
                .userId(task.getUser() != null
                        ? task.getUser().getId()
                        : null)
                .tags(mapTags(task.getTags()))
                .build();
    }


    private static List<String> mapTags(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()){
            return Collections.emptyList();
        }
        return tags.stream()
                .map(Tag::getTagName)
                .toList();
    }
}
