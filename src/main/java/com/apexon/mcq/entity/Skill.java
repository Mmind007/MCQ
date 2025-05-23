package com.apexon.mcq.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skillId;
    private String skillName;
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<Question> questions;

}
