package com.gwiazda.earthquake.infrastructure;

import com.gwiazda.earthquake.application.NearbyEarthquakesDto;

class ConsoleUserInterface {

    private static final String SEARCH_PROMPT = "> ";

    void displayNearbyEarthquakes(NearbyEarthquakesDto nearbyEarthquakesDto) {
        nearbyEarthquakesDto.getAll().forEach(earthquakeDistanceDto ->
                System.out.println(earthquakeDistanceDto.getTitle() + " || " + earthquakeDistanceDto.getDistanceInKm()));
    }

    void display(String textToDisplay) {
        System.out.print(textToDisplay + SEARCH_PROMPT);
    }
}
