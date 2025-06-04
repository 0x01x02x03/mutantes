package com.magneto.mutantes.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.magneto.mutantes.model.DnaRequest;
import com.magneto.mutantes.service.MutantService;

class MutantControllerTest {

    private MutantService mutantService;
    private MutantController mutantController;

    @BeforeEach
    public void setUp() {
        mutantService = mock(MutantService.class);
        mutantController = new MutantController(mutantService);
    }

    @Test
    void checkMutant_shouldReturnBadRequest_whenRequestIsNull() {
        ResponseEntity<Void> response = mutantController.checkMutant(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void checkMutant_shouldReturnBadRequest_whenDnaIsNull() {
        DnaRequest request = new DnaRequest();
        request.setDna(null);
        ResponseEntity<Void> response = mutantController.checkMutant(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void checkMutant_shouldDelegateToServiceAndReturnResponse() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        when(mutantService.processDna(dna)).thenReturn(expectedResponse);

        ResponseEntity<Void> response = mutantController.checkMutant(request);

        assertSame(expectedResponse, response);
        verify(mutantService, times(1)).processDna(dna);
    }
}
