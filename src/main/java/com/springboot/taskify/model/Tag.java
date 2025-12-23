package com.springboot.taskify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {

    @Column(length = 50, unique = true, nullable = false)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks;

}
