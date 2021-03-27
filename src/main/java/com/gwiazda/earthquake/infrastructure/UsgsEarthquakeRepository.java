package com.gwiazda.earthquake.infrastructure;

import com.gwiazda.earthquake.domain.ProblemWithFetchingEarthquakesDataException;
import com.gwiazda.earthquake.domain.Earthquake;
import com.gwiazda.earthquake.domain.EarthquakeRepository;
import com.gwiazda.earthquake.domain.PlaceCoordinates;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class UsgsEarthquakeRepository implements EarthquakeRepository {

    private final UsgsEarthquakeRestClient usgsEarthquakeRestClient;

    UsgsEarthquakeRepository(UsgsEarthquakeRestClient usgsEarthquakeRestClient) {
        this.usgsEarthquakeRestClient = usgsEarthquakeRestClient;
    }

    @Override
    public List<Earthquake> findEarthquakes() {
        UsgsEarthquakeRestDto usgsEarthquakesRestDto;
        try {
            usgsEarthquakesRestDto = usgsEarthquakeRestClient.getEarthquakesFromLast30Days();
        } catch (IOException | InterruptedException e) {
            throw new ProblemWithFetchingEarthquakesDataException();
        }
        return mapToEarthquakes(usgsEarthquakesRestDto);
    }

    private List<Earthquake> mapToEarthquakes(UsgsEarthquakeRestDto usgsEarthquakesRestDto) {
        return usgsEarthquakesRestDto.getFeatures()
                .stream()
                .map(this::createEarthquake)
                .collect(Collectors.toList());
    }

    private Earthquake createEarthquake(UsgsEarthquakeFeatureRestDto feature) {
        return new Earthquake(feature.getProperties().getTitle(),
                new PlaceCoordinates(feature.getGeometry().getCoordinates().get(1),
                        feature.getGeometry().getCoordinates().get(0)));
    }
}
