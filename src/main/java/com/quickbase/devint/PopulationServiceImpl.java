package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class PopulationServiceImpl implements PopulationService {

    private final DBManager dbManager;
    private final IStatService statService;

    public PopulationServiceImpl(DBManager dbManager, IStatService statService) {
        this.dbManager = dbManager;
        this.statService = statService;
    }

    public Iterable<Pair<String, Integer>> getCountryPopulations() {
        Map<String, Pair<String, Integer>> map = new HashMap<>();
        statService.getCountryPopulations().forEach(p -> map.put(p.getKey(), p));
        dbManager.getCountryPopulations().forEach(p -> map.put(p.getKey(), p));
        return map.values();
    }
}
