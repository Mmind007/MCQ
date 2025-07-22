package com.apexon.mcq.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "learning_progress")
public class LearningProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "User is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Learning Material is required")
    @Column(name = "learning_material_id", nullable = false)
    private Long learningMaterialId;

    @NotNull(message = "Area is required")
    @Column(name = "area_id", nullable = false)
    private Long areaId;


//    @NotNull(message = "User is required")
//    @ManyToOne(optional = false)
//    private User user;
//
//    @NotNull(message = "Learning Material")
//    @OneToOne(optional = false)
//    private LearningMaterial learningMaterial;
//
//    @NotNull(message = "Area is required")
//    @ManyToOne(optional = false)
//    private Area area;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "Summary can't exceed 5000 characters")
    private String summary;

    @NotNull(message = "Progress percentage is required")
    @Min(0)
    @Max(100)
    @Column(name = "progress_percentage", nullable = false)
    private long progressPercentage = 0;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;


}
