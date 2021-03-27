package com.gwiazda.earthquake.infrastructure

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.gwiazda.earthquake.application.EarthquakeApplicationService
import com.gwiazda.earthquake.application.NearbyEarthquakesDto
import com.gwiazda.earthquake.domain.EarthquakeRepository
import com.gwiazda.earthquake.domain.PlaceCoordinates
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

class EarthquakeApplicationServiceIntegrationSpec extends Specification {

    private static final PlaceCoordinates CRACOW_COORDINATES = new PlaceCoordinates(50.049683, 19.944544)
    private static final String PEARBLOSSOM_EARTHQUAKE_TITLE = "M 2.1 - 6km SSW of Pearblossom, CA"
    private static final String HAWAII_EARTHQUAKE_TITLE = "M 1.9 - 7 km S of Volcano, Hawaii"
    private static final String EARTHQUAKE_SERVICE_URL_PATH = "/all_month.geojson"
    public static final int PEARBLOSSOM_EARTHQUAKE_DISTANCE_TO_CRACOW = 9745

    private WireMockServer wireMockServer
    private UsgsEarthquakeRestClient usgsEarthquakeRestClient
    private EarthquakeRepository earthquakeRepository
    private EarthquakeApplicationService earthquakeApplicationService

    def setup() {
        setTestContext()
        wireMockServer = new WireMockServer(wireMockConfig().port(8089))
        wireMockServer.start()
        usgsEarthquakeRestClient = new UsgsEarthquakeRestClient(new EnvironmentProperties())
        earthquakeRepository = new UsgsEarthquakeRepository(usgsEarthquakeRestClient)
        earthquakeApplicationService = new EarthquakeApplicationService(earthquakeRepository)
    }

    def cleanup() {
        wireMockServer.stop()
    }

    def "Should return closest earthquake to Cracow"() {
        given: "There is earthquake service responding"
            stubUsgsEarthquakesResponse()
        when: "User searches for nearby Cracow earthquake"
            int nrOfEarthquakesToGet = 1
            NearbyEarthquakesDto nearbyEarthquakesDto = earthquakeApplicationService.nearbyEarthquakesTo(CRACOW_COORDINATES, nrOfEarthquakesToGet)
        then: "Information about Pearblossom earthquake is returned"
            nearbyEarthquakesDto.getAll().size() == 1
            nearbyEarthquakesDto.getAll()[0].getTitle() == PEARBLOSSOM_EARTHQUAKE_TITLE
            nearbyEarthquakesDto.getAll()[0].getDistanceInKm() == PEARBLOSSOM_EARTHQUAKE_DISTANCE_TO_CRACOW
    }

    private StubMapping stubUsgsEarthquakesResponse() {
        wireMockServer.stubFor(get(EARTHQUAKE_SERVICE_URL_PATH)
                .willReturn(aResponse()
                        .withBody(usgsEarthquakesResponse())
                        .withHeader("Content-Type", "application/json")
                ))
    }

    private def usgsEarthquakesResponse() {
        return """
         {
          type: "FeatureCollection",
          metadata: {
            generated: 1602852916000,
            url: "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson",
            title: "USGS All Earthquakes, Past Month",
            status: 200,
            api: "1.10.3",
            count: 13116
          },
          features: [
            {
              type: "Feature",
              properties: {
                mag: 2.1,
                place: "6km SSW of Pearblossom, CA",
                time: 1602852457140,
                updated: 1602852691699,
                tz: null,
                url: "https://earthquake.usgs.gov/earthquakes/eventpage/ci39665088",
                detail: "https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ci39665088.geojson",
                felt: null,
                cdi: null,
                mmi: null,
                alert: null,
                status: "automatic",
                tsunami: 0,
                sig: 68,
                net: "ci",
                code: "39665088",
                ids: ",ci39665088,",
                sources: ",idci,",
                types: ",nearby-cities,origin,phase-data,scitech-link,",
                nst: 51,
                dmin: 0.06909,
                rms: 0.2,
                gap: 25,
                magType: "ml",
                type: "earthquake",
                title: "${PEARBLOSSOM_EARTHQUAKE_TITLE}"
              },
              geometry: {
                type: "Point",
                coordinates: [
                  -117.929,
                  34.4591667,
                  8.33
                ]
              },
              id: "ci39665088"
            },
            {
              type: "Feature",
              properties: {
                mag: 1.94,
                place: "7 km S of Volcano, Hawaii",
                time: 1602852320250,
                updated: 1602852651230,
                tz: null,
                url: "https://earthquake.usgs.gov/earthquakes/eventpage/hv72183086",
                detail: "https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/hv72183086.geojson",
                felt: null,
                cdi: null,
                mmi: null,
                alert: null,
                status: "automatic",
                tsunami: 0,
                sig: 58,
                net: "hv",
                code: "72183086",
                ids: ",hv72183086,",
                sources: ",hv,",
                types: ",origin,phase-data,",
                nst: 23,
                dmin: null,
                rms: 0.129999995,
                gap: 91,
                magType: "ml",
                type: "earthquake",
                title: "${HAWAII_EARTHQUAKE_TITLE}"
              },
              geometry: {
                type: "Point",
                coordinates: [
                  -155.231506347656,
                  19.3724994659424,
                  0.620000004768372
                ]
              },
              id: "hv72183086"
            }
          ]
        }
    """
    }

    def setTestContext() {
        System.setProperty("NEARBY_EARTHQUAKES_ENV", "TEST")
    }
}
