package com.apexon.mcq.repository;

import com.apexon.mcq.entity.Option;
import com.apexon.mcq.entity.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRepository extends JpaRepository<Option,Long> {
    List<Option> findByQuestion(Question question);


}
