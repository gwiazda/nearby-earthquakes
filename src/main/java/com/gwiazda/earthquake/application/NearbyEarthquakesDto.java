package com.gwiazda.earthquake.application;

import com.gwiazda.earthquake.domain.EarthquakeDistance;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class NearbyEarthquakesDto {

    private final List<EarthquakeDistanceDto> earthquakeDistanceDtos;

    static NearbyEarthquakesDto from(List<EarthquakeDistance> earthquakeDistances) {
        List<EarthquakeDistanceDto> earthquakeDistancesDto = earthquakeDistances.stream()
                .map(EarthquakeDistanceDto::from)
                .collect(toUnmodifiableList());

        return new NearbyEarthquakesDto(earthquakeDistancesDto);
    }

    public NearbyEarthquakesDto(List<EarthquakeDistanceDto> earthquakeDistanceDtos) {
        this.earthquakeDistanceDtos = earthquakeDistanceDtos;
    }

    public List<EarthquakeDistanceDto> getAll() {
        return earthquakeDistanceDtos;
    }
}
