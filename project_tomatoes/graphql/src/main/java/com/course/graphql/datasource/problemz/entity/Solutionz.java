package com.course.graphql.datasource.problemz.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "solutionz")
public class Solutionz {

    @Id
    private String id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private String content;
    private String category;
    private int voteGoodCount;
    private int voteBadCount;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

    @ManyToOne
    @JoinColumn(name = "problemz_id", nullable = false)
    private Problemz problemz;

}
