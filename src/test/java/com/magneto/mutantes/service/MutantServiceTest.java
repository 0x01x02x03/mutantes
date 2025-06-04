package com.magneto.mutantes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

class MutantServiceTest {

    @Test
    void testProcessDna() {
        // Preparar
        MutantService mutantService = mock(MutantService.class);
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        when(mutantService.processDna(dna)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Void> response = mutantService.processDna(dna);

        // Assert
        assertEquals(expectedResponse, response);
        verify(mutantService, times(1)).processDna(dna);
    }
}
