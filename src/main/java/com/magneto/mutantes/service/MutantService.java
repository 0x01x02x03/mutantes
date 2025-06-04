package com.magneto.mutantes.service;

import org.springframework.http.ResponseEntity;

public interface MutantService {

    ResponseEntity<Void> processDna(String[] dna);
}
