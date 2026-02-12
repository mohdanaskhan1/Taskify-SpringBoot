//package com.springboot.taskify.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//public class User extends BaseEntity {
//
//    @NotBlank
//    @Size(min = 3, max = 30)
//    @Column(nullable = false, unique = true, length = 30)
//    private String username;
//
//    @NotBlank
//    @Size(min = 8)
//    @Column(nullable = false)
//    private String password;
//
//    @Size(max = 50)
//    @Column(length = 50)
//    private String firstName;
//
//    @Size(max = 50)
//    @Column(length = 50)
//    private String lastName;
//
//    @Email
//    @Size(max = 50)
//    @Column(length = 50, unique = true)
//    private String email;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Task> tasks;
//
//
//}
