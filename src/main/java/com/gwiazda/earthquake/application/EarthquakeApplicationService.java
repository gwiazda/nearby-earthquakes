package com.gwiazda.earthquake.application;

import com.gwiazda.earthquake.domain.Earthquake;
import com.gwiazda.earthquake.domain.EarthquakeDistance;
import com.gwiazda.earthquake.domain.EarthquakeDistanceService;
import com.gwiazda.earthquake.domain.EarthquakeRepository;
import com.gwiazda.earthquake.domain.PlaceCoordinates;

import java.util.List;

public class EarthquakeApplicationService {

    private final EarthquakeRepository earthquakeRepository;

    public EarthquakeApplicationService(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
    }

    public NearbyEarthquakesDto nearbyEarthquakesTo(PlaceCoordinates placeCoordinates, int nrOfEarthquakesToGet) {
        List<Earthquake> earthquakes = earthquakeRepository.findEarthquakes();
        EarthquakeDistanceService earthquakeDistanceService = new EarthquakeDistanceService();
        List<EarthquakeDistance> earthquakeDistances = earthquakeDistanceService.
                findClosestEarthquakesTo(placeCoordinates, earthquakes, nrOfEarthquakesToGet);
        return NearbyEarthquakesDto.from(earthquakeDistances);
    }
}
