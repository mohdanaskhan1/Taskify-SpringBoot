package com.springboot.taskify.controller;

import com.springboot.taskify.dto.TagDTO;
import com.springboot.taskify.service.TagService;
import com.springboot.taskify.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/tags")

public class TagController {
    private final TagService tagService;
    private final TaskService taskService;

    public TagController(TagService tagService, TaskService taskService) {
        this.tagService = tagService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags(@PathVariable Long taskId) {
        taskService.getTaskById(taskId);
        return ResponseEntity.ok(tagService.getAllTagsForTask(taskId));
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagDTO> getTag(@PathVariable Long taskId, @PathVariable Long tagId) {
        taskService.getTaskById(taskId);
        return ResponseEntity.ok(tagService.getTagForTask(taskId, tagId));
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@PathVariable Long taskId, @RequestBody TagDTO tagDTO) {
        taskService.getTaskById(taskId);
        return new ResponseEntity<>(tagService.createTagForTask(taskId, tagDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long taskId, @PathVariable Long tagId, @RequestBody TagDTO tagDTO) {
        taskService.getTaskById(taskId);
        return ResponseEntity.ok(tagService.updateTaskForTag(taskId, tagId, tagDTO));
    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable Long taskId, @PathVariable Long tagId) {
        taskService.getTaskById(taskId);
        tagService.deleteTagForTask(taskId, tagId);
    }


}