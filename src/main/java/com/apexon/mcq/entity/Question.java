package com.apexon.mcq.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
//annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private  Long questionId;

    private String questionStatement;
    private String explanation;

    private String difficulty;

    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Option> options = new ArrayList<>();


    @Column(nullable = false)
    private String correctOption; // values like "A", "B", "C", or "D"

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

}
