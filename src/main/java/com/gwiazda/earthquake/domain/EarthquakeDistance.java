package com.gwiazda.earthquake.domain;

public class EarthquakeDistance {
    private final String title;
    private final PlaceCoordinates placeCoordinates;
    private final PlaceCoordinates earthquakeCoordinates;
    private final int distanceInKm;

    EarthquakeDistance(String title, PlaceCoordinates placeCoordinates, PlaceCoordinates earthquakeCoordinates, int distanceInKm) {
        this.title = title;
        this.placeCoordinates = placeCoordinates;
        this.earthquakeCoordinates = earthquakeCoordinates;
        this.distanceInKm = distanceInKm;
    }

    public String title() {
        return title;
    }

    PlaceCoordinates placeCoordinates() {
        return placeCoordinates;
    }

    PlaceCoordinates earthquakeCoordinates() {
        return earthquakeCoordinates;
    }

    public int distanceInKm() {
        return distanceInKm;
    }
}
