package com.magneto.mutantes.controller;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.magneto.mutantes.service.StatsService;

class StatsControllerTest {

    private StatsService statsService;
    private StatsController statsController;

    @BeforeEach
    public void setUp() {
        statsService = mock(StatsService.class);
        statsController = new StatsController(statsService);
    }

    @Test
    void getDnaStats_ReturnsStatsMapWithStatusOk() {
        Map<String, Object> mockStats = new HashMap<>();
        mockStats.put("count_mutant_dna", 40);
        mockStats.put("count_human_dna", 100);
        mockStats.put("ratio", 0.4);

        when(statsService.getDnaStats()).thenReturn(mockStats);

        ResponseEntity<Map<String, Object>> response = statsController.getDnaStats();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(mockStats, response.getBody());
        verify(statsService, times(1)).getDnaStats();
    }
}
