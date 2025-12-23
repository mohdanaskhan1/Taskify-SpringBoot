package com.springboot.taskify.mapper;

import com.springboot.taskify.dto.TagDTO;
import com.springboot.taskify.model.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagMapper {
    public static Tag toEntity(TagDTO tagDTO) {
        if (tagDTO == null) return null;
        return Tag.builder()
                .tagName(tagDTO.getTagName()).
                build();
    }

    public static TagDTO toDTO(Tag tag) {
        if (tag == null) return null;
        return TagDTO.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
