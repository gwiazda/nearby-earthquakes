package com.gwiazda.earthquake.infrastructure

import com.gwiazda.earthquake.application.EarthquakeApplicationService
import com.gwiazda.earthquake.application.EarthquakeDistanceDto
import com.gwiazda.earthquake.application.NearbyEarthquakesDto
import com.gwiazda.earthquake.domain.PlaceCoordinates
import spock.lang.Specification

import static java.lang.Double.parseDouble

class EarthquakeApplicationConsoleSpec extends Specification {

    private static final String QUIT_COMMAND = ":quit";
    private static final String ETNA_EARTHQUAKE_TITLE = "Etna earthquake title"
    private static final int DISTANCE_BETWEEN_ETNA_AND_CRACOW = 1423
    private static final String USER_INPUT_LATITUDE = "50.049683"
    private static final String USER_INPUT_LONGITUDE = "19.944544"
    private static final String USER_INPUT_COORDINATES = USER_INPUT_LATITUDE + " " + USER_INPUT_LONGITUDE

    private ConsoleUserInterface consoleUserInterface
    private EarthquakeApplicationService earthquakeApplicationService
    private EarthquakeApplicationConsole earthquakeApplicationConsole

    def setup(){
        consoleUserInterface = Mock()
        earthquakeApplicationService = Mock()
        earthquakeApplicationConsole = new EarthquakeApplicationConsole(consoleUserInterface, earthquakeApplicationService)
    }

    def "Should quit app when user ask for"() {
        given: "User wants to quit app"
            invokeSystemInputForUser(QUIT_COMMAND)
        when: "App is running"
            earthquakeApplicationConsole.start()
        then: "App is closed"
            println "App closing"
    }

    def "Should return list of nearby earthquakes after user provided coordinates"() {
        given: "User provides coordinates"
            userInputCoordinates(USER_INPUT_COORDINATES)
        and: "Application returns list of nearby earthquakes"
            mockReturningListOfNearbyEarthquakes()
        when: "Application is running"
            earthquakeApplicationConsole.start()
        then: "Information about earthquakes are displayed"
            1 * consoleUserInterface.displayNearbyEarthquakes(_) >> {
                NearbyEarthquakesDto nearbyEarthquakesDto = it[0]
                assert nearbyEarthquakesDto.getAll().size() == 1
                assert nearbyEarthquakesDto.getAll()[0].title == ETNA_EARTHQUAKE_TITLE
                assert nearbyEarthquakesDto.getAll()[0].distanceInKm == DISTANCE_BETWEEN_ETNA_AND_CRACOW
            }
    }

    def "Should let user to input coordinates second time if first time are incorrect"() {
        given: "User first time provides incorrect coordinates"
            asFirstTimeUserProvidesIncorrectCoordinates()
        when: "Application is running"
            earthquakeApplicationConsole.start()
        then: "Information about earthquakes is displayed"
            1 * consoleUserInterface.displayNearbyEarthquakes(_)
    }

    def mockReturningListOfNearbyEarthquakes() {
        earthquakeApplicationService.nearbyEarthquakesTo(
                new PlaceCoordinates(parseDouble(USER_INPUT_LATITUDE), parseDouble(USER_INPUT_LONGITUDE)), 10) >>
                new NearbyEarthquakesDto(List.of(
                        new EarthquakeDistanceDto(ETNA_EARTHQUAKE_TITLE, DISTANCE_BETWEEN_ETNA_AND_CRACOW)))
    }

    def asFirstTimeUserProvidesIncorrectCoordinates(){
        String searchPhraseAndQuit = "incorrectCoordinates" + System.getProperty("line.separator") + USER_INPUT_COORDINATES + System.getProperty("line.separator") + QUIT_COMMAND;
        invokeSystemInputForUser(searchPhraseAndQuit);
    }

    def userInputCoordinates(String latitudeLongitude) {
        String searchPhraseAndQuit = latitudeLongitude + System.getProperty("line.separator") + QUIT_COMMAND;
        invokeSystemInputForUser(searchPhraseAndQuit);
    }

    def invokeSystemInputForUser(String searchPhraseAndQuit) {
        InputStream quitCommand = new ByteArrayInputStream(searchPhraseAndQuit.getBytes());
        System.setIn(quitCommand);
    }
}
