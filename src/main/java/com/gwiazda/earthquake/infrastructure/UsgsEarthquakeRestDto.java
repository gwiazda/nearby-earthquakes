package com.gwiazda.earthquake.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class UsgsEarthquakeRestDto {

    private List<UsgsEarthquakeFeatureRestDto> features;

    public List<UsgsEarthquakeFeatureRestDto> getFeatures() {
        return features;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class UsgsEarthquakeFeatureRestDto {

    private UsgsEarthquakePropertiesRestDto properties;
    private UsgsEarthquakeGeometryRestDto geometry;

    public UsgsEarthquakePropertiesRestDto getProperties() {
        return properties;
    }

    public UsgsEarthquakeGeometryRestDto getGeometry() {
        return geometry;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class UsgsEarthquakePropertiesRestDto {

    private String title;

    public String getTitle() {
        return title;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class UsgsEarthquakeGeometryRestDto {

    private List<Double> coordinates;

    public List<Double> getCoordinates() {
        return coordinates;
    }
}