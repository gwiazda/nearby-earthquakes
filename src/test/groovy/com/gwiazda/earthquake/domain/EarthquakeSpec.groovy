package com.gwiazda.earthquake.domain

import spock.lang.Specification
import spock.lang.Unroll

class EarthquakeSpec extends Specification {

    private static final String ETNA_EARTHQUAKE_TITLE = "Etna earthquake title"
    private static final String NAGANO_EARTHQUAKE_TITLE = "Nagano earthquake title"
    private static final int DISTANCE_BETWEEN_ETNA_AND_CRACOW = 1423
    private static final int DISTANCE_BETWEEN_NAGANO_AND_CRACOW = 8634
    private static final PlaceCoordinates ETNA_COORDINATES = new PlaceCoordinates(37.750833, 14.993889)
    private static final PlaceCoordinates NAGANO_COORDINATES = new PlaceCoordinates(36.65139, 138.18111)

    @Unroll
    def "Should calculate distance from earthquake to given place"() {
        given: "There is earthquake in given place"
            Earthquake earthquake = new Earthquake(title, earthquakeCoordinates)
        when: "Earthquake returns distance to given place"
            PlaceCoordinates cracowCoordinates = new PlaceCoordinates(50.049683, 19.944544)
            EarthquakeDistance earthquakeDistances = earthquake.distanceTo(cracowCoordinates)
        then: "Distance data are correct"
            earthquakeDistances.title() == title
            earthquakeDistances.placeCoordinates() == cracowCoordinates
            earthquakeDistances.distanceInKm() == distanceToCracow
        where:
            title                   | earthquakeCoordinates | distanceToCracow
            ETNA_EARTHQUAKE_TITLE   | ETNA_COORDINATES      | DISTANCE_BETWEEN_ETNA_AND_CRACOW
            NAGANO_EARTHQUAKE_TITLE | NAGANO_COORDINATES    | DISTANCE_BETWEEN_NAGANO_AND_CRACOW
    }
}
