package com.magneto.mutantes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.magneto.mutantes.repository.DnaSequenceRepository;
import com.magneto.mutantes.util.DnaPersistenceUtil;
import com.magneto.mutantes.util.HashUtil;
import com.magneto.mutantes.util.MutantDetector;

@Service
public class MutantServiceImpl implements MutantService {

    private final MutantDetector detector = new MutantDetector();
    private final DnaSequenceRepository dnaSequenceRepository;

    public MutantServiceImpl(DnaSequenceRepository dnaSequenceRepository) {
        this.dnaSequenceRepository = dnaSequenceRepository;
    }

    @Override
    public ResponseEntity<Void> processDna(String[] dna) {
        String dnaCombined = String.join("", dna);
        String dnaHash = HashUtil.generateSha256Hash(dnaCombined);

        if (existsInRepository(dnaHash)) {
            Boolean isMutant = getMutantStatusFromRepositories(dnaHash);
            if (isMutant != null) {
                return buildResponse(isMutant);
            }
        }
        boolean isMutant = detector.isMutant(dna);
        DnaPersistenceUtil.saveDnaRecord(dnaSequenceRepository, dnaHash, isMutant);
        return buildResponse(isMutant);
    }

    private boolean existsInRepository(String dnaHash) {
        return dnaSequenceRepository.existsById(dnaHash);
    }

    private Boolean getMutantStatusFromRepositories(String dnaHash) {
        var seq = dnaSequenceRepository.findById(dnaHash);
        if (seq.isPresent()) {
            return seq.get().isMutant();
        }
        return null;
    }

    private ResponseEntity<Void> buildResponse(boolean isMutant) {
        if (isMutant) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
