package com.springboot.taskify.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TagDTO {
    private Long id;
    private String tagName;
}
