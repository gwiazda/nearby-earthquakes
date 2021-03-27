package com.gwiazda.earthquake.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class EarthquakeDistanceService {

    public List<EarthquakeDistance> findClosestEarthquakesTo(PlaceCoordinates placeCoordinates,
                                                             List<Earthquake> earthquakes,
                                                             int numberOfEarthquakesToFind) {
        return earthquakes.stream()
                .map(earthquake -> earthquake.distanceTo(placeCoordinates))
                .sorted(comparingInt(EarthquakeDistance::distanceInKm))
                .filter(distinctByEarthquakeCoordinates())
                .limit(numberOfEarthquakesToFind)
                .collect(Collectors.toList());
    }

    private Predicate<EarthquakeDistance> distinctByEarthquakeCoordinates() {
        Function<EarthquakeDistance, PlaceCoordinates> getEarthQuakeCoordinates = EarthquakeDistance::earthquakeCoordinates;
        Map<PlaceCoordinates, Boolean> existingCoordinates = new HashMap<>();
        return earthquakeDistance ->
                existingCoordinates.putIfAbsent(getEarthQuakeCoordinates.apply(earthquakeDistance), Boolean.TRUE) == null;
    }
}
