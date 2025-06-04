package com.magneto.mutantes.util;

import com.magneto.mutantes.model.DnaSequence;
import com.magneto.mutantes.repository.DnaSequenceRepository;

public class DnaPersistenceUtil {

    private DnaPersistenceUtil() {
    }

    public static void saveDnaRecord(DnaSequenceRepository dnaSequenceRepository, String dnaHash, boolean isMutant) {
        DnaSequence dnaSequence = new DnaSequence();
        dnaSequence.setMutant(isMutant);
        dnaSequence.setDnaHash(dnaHash);
        try {
            dnaSequenceRepository.save(dnaSequence);
        } catch (Exception e) {
            System.err.println("Error saving DNA sequence: " + e.getMessage());
        }
    }
}
