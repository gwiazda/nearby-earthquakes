package com.gwiazda.earthquake.domain;

import java.util.Objects;

public class PlaceCoordinates {

    private final double latitude;
    private final double longitude;

    public PlaceCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    double latitude() {
        return latitude;
    }

    double longitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceCoordinates that = (PlaceCoordinates) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
