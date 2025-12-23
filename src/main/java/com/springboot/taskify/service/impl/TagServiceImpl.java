package com.springboot.taskify.service.impl;

import com.springboot.taskify.dto.TagDTO;
import com.springboot.taskify.exception.ResourceNotFoundException;
import com.springboot.taskify.exception.TaskAPIException;
import com.springboot.taskify.mapper.TagMapper;
import com.springboot.taskify.model.Tag;
import com.springboot.taskify.model.Task;
import com.springboot.taskify.repository.TagRepository;
import com.springboot.taskify.repository.TaskRepository;
import com.springboot.taskify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TagServiceImpl(TaskRepository taskRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public List<TagDTO> getAllTagsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        return task.getTags().stream().map(TagMapper::toDTO).toList();
    }

    @Override
    public TagDTO getTagForTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (!task.getTags().contains(tag)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Tag doesnt belong to this Task");
        }
        return TagMapper.toDTO(tag);
    }

    @Override
    public TagDTO createTagForTask(Long taskId, TagDTO tagDTO) {
        Tag tag = tagRepository.findByTagName(tagDTO.getTagName());
        if (tag == null) {
            tag = TagMapper.toEntity(tagDTO);
            tag = tagRepository.save(tag);
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        task.getTags().add(tag);
        taskRepository.save(task);
        return TagMapper.toDTO(tag);
    }

    @Override
    public TagDTO updateTaskForTag(Long taskId, Long tagId, TagDTO tagDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (!tag.getTagName().equals(tagDTO.getTagName()) &&
                tagRepository.existsByTagName(tagDTO.getTagName())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Tag name already exists");
        }
        if (!task.getTags().contains(tag)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Tag doesnt belong to this Task");
        }
        tag.setTagName(tagDTO.getTagName());
        return TagMapper.toDTO(tagRepository.save(tag));
    }

    @Override
    public void deleteTagForTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (!task.getTags().contains(tag)) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Tag doesnt belong to this Task");
        }
        task.getTags().remove(tag);
        taskRepository.save(task);
    }
}
