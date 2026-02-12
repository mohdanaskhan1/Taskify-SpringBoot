package com.springboot.taskify.service.impl;

import com.springboot.taskify.dto.TaskDTO;
import com.springboot.taskify.exception.ResourceNotFoundException;
import com.springboot.taskify.mapper.TaskMapper;
import com.springboot.taskify.model.Tag;
import com.springboot.taskify.model.Task;
import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.repository.TagRepository;
import com.springboot.taskify.repository.TaskRepository;
import com.springboot.taskify.repository.UserRepository;
import com.springboot.taskify.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDTO> getAllTask() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task", "id", taskId));
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = TaskMapper.toTask(taskDTO);
        UserEntity user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", taskDTO.getUserId()));
        task.setUser(user);
        return TaskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Transactional
    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task", "id", taskId));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        if (taskDTO.getUserId() != null) {
            UserEntity user = userRepository.findById(taskDTO.getUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User", "id", taskDTO.getId()));
            task.setUser(user);
        }
        if (taskDTO.getTags() != null) {
            List<String> tags = taskDTO.getTags();
            Set<Tag> tagSet = tags.stream()
                    .map(tagRepository::findByTagName)
                    .collect(Collectors.toSet());
            task.setTags(tagSet);
        }
        return TaskMapper.toDto(taskRepository.save(task));
    }


}
