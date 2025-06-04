package com.magneto.mutantes.service;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.magneto.mutantes.repository.DnaSequenceRepository;

public class StatsServiceImplTest {

    private DnaSequenceRepository dnaSequenceRepository;
    private StatsServiceImpl statsService;

    @Before
    public void setUp() {
        dnaSequenceRepository = mock(DnaSequenceRepository.class);
        statsService = new StatsServiceImpl(dnaSequenceRepository);
    }

    @Test
    public void testGetDnaStats_NormalCase() {
        when(dnaSequenceRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaSequenceRepository.countByIsMutant(false)).thenReturn(60L);

        Map<String, Object> stats = statsService.getDnaStats();

        assertEquals(40L, stats.get("count_mutant_dna"));
        assertEquals(60L, stats.get("count_human_dna"));
        assertEquals(0.4, (Double) stats.get("ratio"), 0.0001);
    }

    @Test
    public void testGetDnaStats_ZeroTotal() {
        when(dnaSequenceRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaSequenceRepository.countByIsMutant(false)).thenReturn(0L);

        Map<String, Object> stats = statsService.getDnaStats();

        assertEquals(0L, stats.get("count_mutant_dna"));
        assertEquals(0L, stats.get("count_human_dna"));
        assertEquals(0.0, (Double) stats.get("ratio"), 0.0001);
    }

    @Test
    public void testGetDnaStats_AllMutants() {
        when(dnaSequenceRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaSequenceRepository.countByIsMutant(false)).thenReturn(0L);

        Map<String, Object> stats = statsService.getDnaStats();

        assertEquals(10L, stats.get("count_mutant_dna"));
        assertEquals(0L, stats.get("count_human_dna"));
        assertEquals(1.0, (Double) stats.get("ratio"), 0.0001);
    }

    @Test
    public void testGetDnaStats_AllHumans() {
        when(dnaSequenceRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaSequenceRepository.countByIsMutant(false)).thenReturn(15L);

        Map<String, Object> stats = statsService.getDnaStats();

        assertEquals(0L, stats.get("count_mutant_dna"));
        assertEquals(15L, stats.get("count_human_dna"));
        assertEquals(0.0, (Double) stats.get("ratio"), 0.0001);
    }
}
