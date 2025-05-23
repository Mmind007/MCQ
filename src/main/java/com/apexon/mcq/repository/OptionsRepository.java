package com.apexon.mcq.repository;

import com.apexon.mcq.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository extends JpaRepository<Option,Long> {
}
