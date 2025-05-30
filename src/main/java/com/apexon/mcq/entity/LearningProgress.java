package com.apexon.mcq.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

//    @NotNull(message = "User is required")
    @ManyToOne(optional = false) //NR, add id
    private User user; //call the user service

//    @NotNull(message = "Learning Material")
    @OneToOne(optional = false)
    private LearningMaterial learningMaterial;

    /*@NotNull(message = "Area is required")
    @ManyToOne(optional = false) // NR if id
    //call the service
    private Area area; //may not be in package, prefer adding id*/

//    @NotNull(message = "Date is required")
//    @Column(nullable = false)
    private LocalDate date;
    //auditing fields

//    @Column(columnDefinition = "TEXT")
//    @Size(max = 5000, message = "Summary can't exceed 5000 characters")
    private String summary;

    @NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress cannot be less than 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    @Column(name = "progress_percentage", nullable = false)
    private long progressPercentage = 0;//25 50 75


}
