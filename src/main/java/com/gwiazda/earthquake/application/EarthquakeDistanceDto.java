package com.gwiazda.earthquake.application;

import com.gwiazda.earthquake.domain.EarthquakeDistance;

public class EarthquakeDistanceDto {

    private final String title;
    private final int distanceInKm;

    public static EarthquakeDistanceDto from(EarthquakeDistance earthquakeDistance) {
        return new EarthquakeDistanceDto(earthquakeDistance.title(), earthquakeDistance.distanceInKm());
    }

    public EarthquakeDistanceDto(String title, int distanceInKm) {
        this.title = title;
        this.distanceInKm = distanceInKm;
    }

    public String getTitle() {
        return title;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }
}
