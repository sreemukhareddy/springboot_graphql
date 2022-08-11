package com.course.graphql.datasource.problemz.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "userz")
@Table(name = "userz")
public class Userz {
    @Id
    private String id;
    private String username;
    private String email;
    private String hashedPassword;
    private URL avatar;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private String displayName;
    private boolean active;
    private String userRole;
}
