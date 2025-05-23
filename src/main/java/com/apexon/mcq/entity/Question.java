package com.apexon.mcq.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long questionId;

    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String questionStatement;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String explanation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options;

}
