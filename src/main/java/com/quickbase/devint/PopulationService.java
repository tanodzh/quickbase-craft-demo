package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

public interface PopulationService {
    Iterable<Pair<String, Integer>> getCountryPopulations();
}
