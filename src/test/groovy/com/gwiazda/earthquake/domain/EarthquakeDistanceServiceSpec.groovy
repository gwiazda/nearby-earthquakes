package com.gwiazda.earthquake.domain


import spock.lang.Specification

class EarthquakeDistanceServiceSpec extends Specification {

    private static final String ETNA_EARTHQUAKE_TITLE = "Etna earthquake title"
    private static final String SECOND_ETNA_EARTHQUAKE_TITLE = "Second Etna earthquake title"
    private static final String NAGANO_EARTHQUAKE_TITLE = "Nagano earthquake title"
    private static final String BEACHPORT_EARTHQUAKE_TITLE = "Beachport earthquake title"
    private static final int DISTANCE_BETWEEN_ETNA_AND_CRACOW = 1423
    private static final PlaceCoordinates ETNA_COORDINATES = new PlaceCoordinates(37.750833, 14.993889)
    private static final PlaceCoordinates NAGANO_COORDINATES = new PlaceCoordinates(36.65139, 138.18111)
    private static final PlaceCoordinates BEACHPORT_COORDINATES = new PlaceCoordinates(-37.392998, 140.166833)
    private static final PlaceCoordinates CRACOW_COORDINATES = new PlaceCoordinates(50.049683, 19.944544)
    private static final ETNA_EARTHQUAKE = new Earthquake(ETNA_EARTHQUAKE_TITLE, ETNA_COORDINATES)
    private static final SECOND_ETNA_EARTHQUAKE = new Earthquake(SECOND_ETNA_EARTHQUAKE_TITLE, ETNA_COORDINATES)
    private static final NAGANO_EARTHQUAKE = new Earthquake(NAGANO_EARTHQUAKE_TITLE, NAGANO_COORDINATES)
    private static final BEACHPORT_EARTHQUAKE = new Earthquake(BEACHPORT_EARTHQUAKE_TITLE, BEACHPORT_COORDINATES)


    def "Should return closest earthquake to given coordinates"() {
        given: "There are earthquakes"
            List<Earthquake> earthquakes = List.of(NAGANO_EARTHQUAKE, ETNA_EARTHQUAKE, BEACHPORT_EARTHQUAKE)
            EarthquakeDistanceService earthquakeDistanceService = new EarthquakeDistanceService()
            int nrOfEarthquakesToFind = 1
        when: "Searching for one nearest earthquake"
            List<EarthquakeDistance> earthquakeDistances = earthquakeDistanceService.findClosestEarthquakesTo(CRACOW_COORDINATES, earthquakes, nrOfEarthquakesToFind)
        then: "Should return information about Etna earthquake"
            earthquakeDistances.size() == nrOfEarthquakesToFind
            earthquakeDistances[0].title() == ETNA_EARTHQUAKE_TITLE
            earthquakeDistances[0].placeCoordinates() == CRACOW_COORDINATES
            earthquakeDistances[0].distanceInKm() == DISTANCE_BETWEEN_ETNA_AND_CRACOW
    }

    def "Should return closest earthquakes to given coordinates in ascending order"() {
        given: "There are earthquakes"
            List<Earthquake> earthquakes = List.of(NAGANO_EARTHQUAKE, ETNA_EARTHQUAKE, BEACHPORT_EARTHQUAKE)
            EarthquakeDistanceService earthquakeDistanceService = new EarthquakeDistanceService()
            int nrOfEarthquakesToFind = 3
        when: "Searching for nearest earthquakes"
            List<EarthquakeDistance> earthquakeDistances = earthquakeDistanceService.findClosestEarthquakesTo(CRACOW_COORDINATES, earthquakes, nrOfEarthquakesToFind)
        then: "Should return information about earthquakes starting from closest"
            earthquakeDistances.size() == nrOfEarthquakesToFind
            earthquakeDistances[0].title() == ETNA_EARTHQUAKE_TITLE
            earthquakeDistances[1].title() == NAGANO_EARTHQUAKE_TITLE
            earthquakeDistances[2].title() == BEACHPORT_EARTHQUAKE_TITLE
    }

    def "Should not return duplicated earthquakes if happened in the same location"() {
        given: "There are earthquakes"
            List<Earthquake> earthquakes = List.of(NAGANO_EARTHQUAKE, ETNA_EARTHQUAKE, SECOND_ETNA_EARTHQUAKE)
            EarthquakeDistanceService earthquakeDistanceService = new EarthquakeDistanceService()
            int nrOfEarthquakesToFind = 2
        when: "Searching for nearest earthquakes"
            List<EarthquakeDistance> earthquakeDistances = earthquakeDistanceService.findClosestEarthquakesTo(CRACOW_COORDINATES, earthquakes, nrOfEarthquakesToFind)
        then: "Should return only one Etna earthquake "
            earthquakeDistances.size() == nrOfEarthquakesToFind
            earthquakeDistances[0].earthquakeCoordinates() == ETNA_COORDINATES
            earthquakeDistances[1].earthquakeCoordinates() != ETNA_COORDINATES
    }
}