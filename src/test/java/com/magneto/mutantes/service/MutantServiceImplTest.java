package com.magneto.mutantes.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.magneto.mutantes.model.DnaSequence;
import com.magneto.mutantes.repository.DnaSequenceRepository;

class MutantServiceImplTest {

    private DnaSequenceRepository dnaSequenceRepository;
    private MutantServiceImpl mutantService;

    @BeforeEach
    public void setUp() {
        dnaSequenceRepository = mock(DnaSequenceRepository.class);
        mutantService = new MutantServiceImpl(dnaSequenceRepository);
    }

    @Test
    void testProcessDna_WhenExistsAndIsMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};
        String hash = mutantService.processDna(dna).getHeaders().toString();

        DnaSequence existing = new DnaSequence();
        existing.setMutant(true);
        existing.setDnaHash(hash);

        when(dnaSequenceRepository.existsById(anyString())).thenReturn(true);
        when(dnaSequenceRepository.findById(anyString())).thenReturn(Optional.of(existing));

        ResponseEntity<Void> response = mutantService.processDna(dna);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testProcessDna_WhenExistsAndIsNotMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};
        DnaSequence existing = new DnaSequence();
        existing.setMutant(false);
        existing.setDnaHash("someHash");

        when(dnaSequenceRepository.existsById(anyString())).thenReturn(true);
        when(dnaSequenceRepository.findById(anyString())).thenReturn(Optional.of(existing));

        ResponseEntity<Void> response = mutantService.processDna(dna);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(dnaSequenceRepository, never()).save(any());
    }

    @Test
    void testProcessDna_WhenNewAndIsMutant() {
        String[] dna = {
            "AAAAAA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CACCTA",
            "TCACTG"
        };

        when(dnaSequenceRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<Void> response = mutantService.processDna(dna);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dnaSequenceRepository).save(any(DnaSequence.class));
    }

    @Test
    void testProcessDna_WhenNewAndIsNotMutant() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAATG",
            "CACCTA",
            "TCACTG"
        };

        when(dnaSequenceRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<Void> response = mutantService.processDna(dna);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(dnaSequenceRepository).save(any(DnaSequence.class));
    }

    @Test
    void testProcessDna_WhenExistsButFindByIdReturnsEmpty() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};
        when(dnaSequenceRepository.existsById(anyString())).thenReturn(true);
        when(dnaSequenceRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = mutantService.processDna(dna);

        // Como findById retorna vacío, debería procesar como nuevo y guardar
        verify(dnaSequenceRepository).save(any(DnaSequence.class));
        // El resultado depende de MutantDetector, pero debe ser OK o FORBIDDEN
        boolean isOkOrForbidden = response.getStatusCode() == HttpStatus.OK
                || response.getStatusCode() == HttpStatus.FORBIDDEN;
        assertEquals(true, isOkOrForbidden);
    }

    @Test
    void testProcessDna_SaveThrowsException() {
        String[] dna = {
            "AAAAAA", // secuencia mutante horizontal
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CACCTA",
            "TCACTG"
        };

        when(dnaSequenceRepository.existsById(anyString())).thenReturn(false);
        // Simular excepción al guardar
        when(dnaSequenceRepository.save(any(DnaSequence.class))).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<Void> response = mutantService.processDna(dna);

        // Debe seguir devolviendo OK porque la excepción se captura internamente
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testExistsInRepository_True() throws Exception {
        // Usar reflect para acceder al método privado
        String hash = "dummyHash";
        when(dnaSequenceRepository.existsById(hash)).thenReturn(true);

        java.lang.reflect.Method method = MutantServiceImpl.class.getDeclaredMethod("existsInRepository", String.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(mutantService, hash);

        assertEquals(true, result);
    }

    @Test
    void testExistsInRepository_False() throws Exception {
        String hash = "dummyHash";
        when(dnaSequenceRepository.existsById(hash)).thenReturn(false);

        java.lang.reflect.Method method = MutantServiceImpl.class.getDeclaredMethod("existsInRepository", String.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(mutantService, hash);

        assertEquals(false, result);
    }

    @Test
    void testGetMutantStatusFromRepositories_PresentTrue() throws Exception {
        String hash = "dummyHash";
        DnaSequence seq = new DnaSequence();
        seq.setMutant(true);
        seq.setDnaHash(hash);

        when(dnaSequenceRepository.findById(hash)).thenReturn(Optional.of(seq));

        java.lang.reflect.Method method = MutantServiceImpl.class.getDeclaredMethod("getMutantStatusFromRepositories", String.class);
        method.setAccessible(true);
        Boolean result = (Boolean) method.invoke(mutantService, hash);

        assertEquals(true, result);
    }

    @Test
    void testGetMutantStatusFromRepositories_PresentFalse() throws Exception {
        String hash = "dummyHash";
        DnaSequence seq = new DnaSequence();
        seq.setMutant(false);
        seq.setDnaHash(hash);

        when(dnaSequenceRepository.findById(hash)).thenReturn(Optional.of(seq));

        java.lang.reflect.Method method = MutantServiceImpl.class.getDeclaredMethod("getMutantStatusFromRepositories", String.class);
        method.setAccessible(true);
        Boolean result = (Boolean) method.invoke(mutantService, hash);

        assertEquals(false, result);
    }

    @Test
    void testGetMutantStatusFromRepositories_Empty() throws Exception {
        String hash = "dummyHash";
        when(dnaSequenceRepository.findById(hash)).thenReturn(Optional.empty());

        java.lang.reflect.Method method = MutantServiceImpl.class.getDeclaredMethod("getMutantStatusFromRepositories", String.class);
        method.setAccessible(true);
        Boolean result = (Boolean) method.invoke(mutantService, hash);

        assertEquals(null, result);
    }

}
