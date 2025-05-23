package com.apexon.mcq.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="options")
@Data
public class Option {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private boolean isCorrect;

        private String explanation;

        private String description;

        @ManyToOne
        @JoinColumn(name = "question_id")
        private Question question;
}
