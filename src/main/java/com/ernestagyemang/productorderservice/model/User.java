package com.ernestagyemang.productorderservice.model;

import com.ernestagyemang.productorderservice.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

