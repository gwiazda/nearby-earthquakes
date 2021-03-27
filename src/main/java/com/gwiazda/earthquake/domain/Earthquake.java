package com.gwiazda.earthquake.domain;


import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public class Earthquake {

    private final String title;
    private final PlaceCoordinates earthquakeCoordinates;

    public Earthquake(String title, PlaceCoordinates earthquakeCoordinates) {
        this.title = title;
        this.earthquakeCoordinates = earthquakeCoordinates;
    }

    EarthquakeDistance distanceTo(PlaceCoordinates placeCoordinates) {
        int distanceInKm = calculateDistanceUsingHaversineFormula(placeCoordinates);
        return new EarthquakeDistance(title, placeCoordinates, earthquakeCoordinates, distanceInKm);
    }

    private int calculateDistanceUsingHaversineFormula(PlaceCoordinates placeCoordinates) {
        int earthRadius = 6371;
        double earthquakeLatitudeInRadians = toRadians(this.earthquakeCoordinates.latitude());
        double placeLatitudeInRadians = toRadians(placeCoordinates.latitude());
        double latitudeDistanceInRadians = toRadians(placeCoordinates.latitude() - this.earthquakeCoordinates.latitude());
        double longitudeDistanceInRadians = toRadians(placeCoordinates.longitude() - this.earthquakeCoordinates.longitude());
        double a = pow(sin(latitudeDistanceInRadians / 2), 2) + pow(sin(longitudeDistanceInRadians / 2), 2)
                * cos(earthquakeLatitudeInRadians) * cos(placeLatitudeInRadians);
        double c = 2 * asin(sqrt(a));
        return (int) (c * earthRadius);
    }
}
