package com.magneto.mutantes.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DnaRequestTest {

    @Test
    void testDnaGetterAndSetter() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT"};
        request.setDna(dna);
        assertArrayEquals(dna, request.getDna());
    }

    @Test
    void testNoArgsConstructor() {
        DnaRequest request = new DnaRequest();
        assertNull(request.getDna());
    }

    @Test
    void testEqualsAndHashCode() {
        DnaRequest req1 = new DnaRequest();
        DnaRequest req2 = new DnaRequest();
        String[] dna = {"ATGCGA"};
        req1.setDna(dna);
        req2.setDna(dna);
        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    void testToString() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATGCGA"};
        request.setDna(dna);
        String str = request.toString();
        assertTrue(str.contains("dna=[ATGCGA]"));
    }
}