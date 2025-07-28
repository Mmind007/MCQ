package com.apexon.mcq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionId implements Serializable {

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "option_id")
    private Integer optionId; //


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionId)) return false;
        OptionId that = (OptionId) o;
        return Objects.equals(questionId, that.questionId) &&
                Objects.equals(optionId, that.optionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, optionId);
    }
}
