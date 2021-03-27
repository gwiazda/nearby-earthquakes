package com.gwiazda.earthquake.infrastructure

import com.gwiazda.earthquake.application.EarthquakeApplicationService
import com.gwiazda.earthquake.domain.ProblemWithFetchingEarthquakesDataException
import com.gwiazda.earthquake.domain.EarthquakeRepository
import com.gwiazda.earthquake.domain.PlaceCoordinates
import spock.lang.Specification

class EarthquakeInfrastructureSpec extends Specification{

    private static final PlaceCoordinates CRACOW_COORDINATES = new PlaceCoordinates(50.049683, 19.944544)
    private static final int NR_OF_EARTHQUAKES_TO_GET = 3

    private UsgsEarthquakeRestClient usgsEarthquakeRestClient
    private EarthquakeRepository earthquakeRepository
    private EarthquakeApplicationService nearbyEarthquakeApplicationService

    def setup(){
        usgsEarthquakeRestClient = Mock()
        earthquakeRepository = new UsgsEarthquakeRepository(usgsEarthquakeRestClient)
        nearbyEarthquakeApplicationService = new EarthquakeApplicationService(earthquakeRepository)
    }

    def "Should throw custom exception when there are problems with reading data from earthquake service"() {
        given: "Rest client throws exception"
            usgsEarthquakeRestClient.earthquakesFromLast30Days >> { throw new IOException() }
        when: "Earthquakes service is invoked"
            nearbyEarthquakeApplicationService.nearbyEarthquakesTo(CRACOW_COORDINATES, NR_OF_EARTHQUAKES_TO_GET)
        then: "Exception is thrown"
            thrown(ProblemWithFetchingEarthquakesDataException)
    }
}
