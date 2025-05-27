package com.apexon.mcq.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "learning_progress")
public class LearningProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Area area;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 1000)
    private String summary;

    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage;

}
