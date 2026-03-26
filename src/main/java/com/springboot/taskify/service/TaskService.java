package com.springboot.taskify.service;

import com.springboot.taskify.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<TaskDTO> getAllTask();

    TaskDTO getTaskById(Long id);

    TaskDTO addTask(TaskDTO taskDTO);

    void deleteTask(Long id);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);
}
