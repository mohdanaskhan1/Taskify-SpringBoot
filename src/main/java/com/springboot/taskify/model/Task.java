package com.springboot.taskify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 5000)
    @Column(columnDefinition = "text")
    private String description;

    @FutureOrPresent
    private Instant dueDate;

    @NotBlank
    @Size(max = 10)
    @Column(length = 10)
    private String priority;


    @NotBlank
    @Size(max = 20)
    @Column(length = 20)
    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
