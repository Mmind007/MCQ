package com.apexon.mcq.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
        @EmbeddedId
        private OptionId id;

        private boolean isCorrect;

        private String explanation;

        private String description;

        @ManyToOne
        @MapsId("questionId")
        @JoinColumn(name = "question_id", referencedColumnName = "question_id")
        @JsonBackReference
        private Question question;

        // ✅ Getter for option label from embedded ID
        public String getOptionLabel() {
                return id != null && id.getOptionId() != null
                        ? String.valueOf((char) ('A' + id.getOptionId())) // A, B, C, D...
                        : null;
        }

        // ✅ Getter for option statement
        public String getOptionStatement() {
                return description;
        }
}