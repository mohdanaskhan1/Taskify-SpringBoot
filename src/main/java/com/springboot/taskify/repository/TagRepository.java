package com.springboot.taskify.repository;

import com.springboot.taskify.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTagName(String tagName);

    boolean existsByTagName(String tagName);
}
