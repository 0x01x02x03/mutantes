package com.magneto.mutantes.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void shouldReturnTrueIfMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void shouldReturnFalseIfHuman() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void shouldReturnFalseForNullInput() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void shouldReturnFalseForEmptyArray() {
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void shouldReturnFalseForNonSquareMatrix() {
        String[] dna = {"ATGC", "CAGT", "TTAT"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void shouldReturnFalseForInvalidCharacters() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTXTGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void shouldReturnTrueForHorizontalMutant() {
        String[] dna = {"AAAAAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void shouldReturnTrueForVerticalMutant() {
        String[] dna = {"ATGCGA", "ATGTGC", "ATATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void shouldReturnTrueForDiagonalMutant() {
        String[] dna = {"ATGCGA", "CAGAGC", "TTAAGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }
}
