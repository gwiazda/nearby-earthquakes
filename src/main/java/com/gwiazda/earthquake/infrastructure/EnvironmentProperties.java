package com.gwiazda.earthquake.infrastructure;

class EnvironmentProperties {

    private static final String ENV = "NEARBY_EARTHQUAKES_ENV";
    private static final String TEST_ENV = "TEST";

    String getEarthquakeServiceUrl(){
        String env = System.getProperty(ENV);
        if (TEST_ENV.equals(env)){
            return "http://localhost:8089/all_month.geojson";
        }
        return "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    }
}
