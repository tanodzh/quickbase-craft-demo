package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.util.Collection;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {
    Connection getConnection();

    Collection<Pair<String, Integer>> getCountryPopulations();
}
