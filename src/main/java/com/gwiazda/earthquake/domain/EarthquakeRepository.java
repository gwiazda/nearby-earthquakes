package com.gwiazda.earthquake.domain;

import java.util.List;

public interface EarthquakeRepository {
    List<Earthquake> findEarthquakes() throws ProblemWithFetchingEarthquakesDataException;
}
