package com.gwiazda.earthquake.infrastructure;

import com.gwiazda.earthquake.application.EarthquakeApplicationService;
import com.gwiazda.earthquake.application.NearbyEarthquakesDto;
import com.gwiazda.earthquake.domain.PlaceCoordinates;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

class EarthquakeApplicationConsole {

    private static final String QUIT_COMMAND = ":quit";
    private static final String SPLIT_SEATS_REGEX = " ";
    private static final int NR_OF_EARTHQUAKES_TO_GET = 10;

    private final ConsoleUserInterface consoleUserInterface;
    private final EarthquakeApplicationService earthquakeApplicationService;

    EarthquakeApplicationConsole(ConsoleUserInterface consoleUserInterface, EarthquakeApplicationService earthquakeApplicationService) {
        this.consoleUserInterface = consoleUserInterface;
        this.earthquakeApplicationService = earthquakeApplicationService;
    }

    public void start() {
        consoleUserInterface.display("Provide latitude ang longitude of a place (e.g.: 50.049683 19.944544) [:quit for exit]");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            PlaceCoordinates placeCoordinates = getUserInput(scanner);
            if (placeCoordinates == null) {
                return;
            }
            NearbyEarthquakesDto nearbyEarthquakesDto = earthquakeApplicationService.nearbyEarthquakesTo(
                    placeCoordinates, NR_OF_EARTHQUAKES_TO_GET);
            consoleUserInterface.displayNearbyEarthquakes(nearbyEarthquakesDto);
            consoleUserInterface.display("You can check another coordinates (e.g.: 50.049683 19.944544) [:quit for exit]");
        }
    }

    private PlaceCoordinates getUserInput(Scanner scanner) {
        while (true) {
            String userInputCoordinates = scanner.nextLine();
            if (shouldExitApplication(userInputCoordinates)) {
                return null;
            }
            Optional<PlaceCoordinates> placeCoordinatesOptional = parseCoordinates(userInputCoordinates);
            if (placeCoordinatesOptional.isPresent()) {
                return placeCoordinatesOptional.get();
            }
        }
    }

    private Optional<PlaceCoordinates> parseCoordinates(String userInputCoordinates) {
        Optional<PlaceCoordinates> placeCoordinates = Optional.empty();
        try {
            String[] coordinates = userInputCoordinates.split(SPLIT_SEATS_REGEX);
            double latitude = parseDouble(coordinates[0]);
            double longitude = parseDouble(coordinates[1]);
            placeCoordinates = Optional.of(new PlaceCoordinates(latitude, longitude));
        } catch (NumberFormatException exc) {
            consoleUserInterface.display("Wrong format. Try one more time (e.g.: 50.049683 19.944544). [:quit for exit]");
        }
        return placeCoordinates;
    }

    private boolean shouldExitApplication(String phraseToSearchFor) {
        return phraseToSearchFor.equals(QUIT_COMMAND);
    }

}
