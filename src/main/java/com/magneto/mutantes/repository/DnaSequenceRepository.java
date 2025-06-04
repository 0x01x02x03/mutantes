package com.magneto.mutantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magneto.mutantes.model.DnaSequence;

@Repository
public interface DnaSequenceRepository extends JpaRepository<DnaSequence, String> {

    long countByIsMutant(boolean isMutant);
}
