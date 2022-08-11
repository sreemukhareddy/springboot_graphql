package com.course.graphql.datasource.problemz.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "userz_token")
public class UserzToken {
    @Id
    private String userId;
    private String authToken;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    @CreationTimestamp
    private LocalDateTime expiryTimestamp;
}
