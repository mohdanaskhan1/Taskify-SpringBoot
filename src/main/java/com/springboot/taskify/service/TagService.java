package com.springboot.taskify.service;

import com.springboot.taskify.dto.TagDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    List<TagDTO> getAllTagsForTask(Long taskId);

    TagDTO getTagForTask(Long taskId, Long tagId);

    TagDTO createTagForTask(Long taskId, TagDTO tagDTO);

    TagDTO updateTaskForTag(Long taskId, Long tagId, TagDTO tagDTO);

    void deleteTagForTask(Long taskId, Long tagId);
}
