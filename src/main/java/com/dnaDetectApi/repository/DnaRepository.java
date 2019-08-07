package com.dnaDetectApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnaDetectApi.entity.Dna;

@Repository
public interface DnaRepository extends JpaRepository<Dna, String>{
	
    public Dna findByDna(String dna);
}
