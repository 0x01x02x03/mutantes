package com.magneto.mutantes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magneto.mutantes.model.DnaRequest;
import com.magneto.mutantes.service.MutantService;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantService mutantService;

    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest request) {
        if (request == null || request.getDna() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return mutantService.processDna(request.getDna());
    }
}
