package com.magneto.mutantes.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.magneto.mutantes.repository.DnaSequenceRepository;

@Service
public class StatsServiceImpl implements StatsService {

    private final DnaSequenceRepository dnaSequenceRepository;

    public StatsServiceImpl(DnaSequenceRepository dnaSequenceRepository) {
        this.dnaSequenceRepository = dnaSequenceRepository;
    }

    @Override
    public Map<String, Object> getDnaStats() {
        long mutantCount = getMutantCount();
        long humanCount = getHumanCount();
        double ratio = calculateRatio(mutantCount, humanCount);
        return getStats(mutantCount, humanCount, ratio);
    }

    private long getMutantCount() {
        return dnaSequenceRepository.countByIsMutant(true);
    }

    private long getHumanCount() {
        return dnaSequenceRepository.countByIsMutant(false);
    }

    private double calculateRatio(long mutantCount, long humanCount) {
        long totalCount = mutantCount + humanCount;
        if (totalCount == 0) {
            return 0.0;
        }
        return (double) mutantCount / totalCount;
    }

    private Map<String, Object> getStats(long mutantCount, long humanCount, double ratio) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", mutantCount);
        stats.put("count_human_dna", humanCount);
        stats.put("ratio", ratio);
        return stats;
    }
}
