package com.gwiazda.earthquake.application

import com.gwiazda.earthquake.domain.Earthquake
import com.gwiazda.earthquake.domain.EarthquakeRepository
import com.gwiazda.earthquake.domain.PlaceCoordinates
import spock.lang.Specification

class EarthquakeApplicationServiceSpec extends Specification {

    private static final String ETNA_EARTHQUAKE_TITLE = "Etna earthquake title"
    private static final String NAGANO_EARTHQUAKE_TITLE = "Nagano earthquake title"
    private static final int NR_OF_EARTHQUAKES_TO_GET = 1
    private static final PlaceCoordinates ETNA_COORDINATES = new PlaceCoordinates(37.750833, 14.993889)
    private static final PlaceCoordinates NAGANO_COORDINATES = new PlaceCoordinates(36.65139, 138.18111)
    private static final PlaceCoordinates CRACOW_COORDINATES = new PlaceCoordinates(50.049683, 19.944544)
    private static final ETNA_EARTHQUAKE = new Earthquake(ETNA_EARTHQUAKE_TITLE, ETNA_COORDINATES)
    private static final NAGANO_EARTHQUAKE = new Earthquake(NAGANO_EARTHQUAKE_TITLE, NAGANO_COORDINATES)
    private static final int DISTANCE_BETWEEN_ETNA_AND_CRACOW = 1423

    private EarthquakeRepository earthquakeRepository
    private EarthquakeApplicationService nearbyEarthquakeApplicationService

    def setup(){
        earthquakeRepository = Mock()
        nearbyEarthquakeApplicationService = new EarthquakeApplicationService(earthquakeRepository)
    }

    def "Should throw custom exception when there are problems with reading data from earthquake service"() {
        given: "Earthquake repository returns information about earthquakes"
            earthquakeRepository.findEarthquakes() >> [ETNA_EARTHQUAKE, NAGANO_EARTHQUAKE]
        when: "Earthquakes service is invoked"
            NearbyEarthquakesDto earthquakesDto = nearbyEarthquakeApplicationService.nearbyEarthquakesTo(CRACOW_COORDINATES, NR_OF_EARTHQUAKES_TO_GET)
        then: "Dto with closes earthquakes is returned"
            earthquakesDto.getAll().size() == 1
            earthquakesDto.getAll()[0].title == ETNA_EARTHQUAKE_TITLE
            earthquakesDto.getAll()[0].distanceInKm == DISTANCE_BETWEEN_ETNA_AND_CRACOW
    }

}
