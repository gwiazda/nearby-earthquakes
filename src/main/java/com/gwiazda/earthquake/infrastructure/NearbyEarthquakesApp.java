package com.gwiazda.earthquake.infrastructure;

import com.gwiazda.earthquake.application.EarthquakeApplicationService;
import com.gwiazda.earthquake.domain.EarthquakeRepository;

public class NearbyEarthquakesApp {
    public static void main(String[] args) {
        EarthquakeApplicationService earthquakeApplicationService = setupApplicationService();
        new EarthquakeApplicationConsole(new ConsoleUserInterface(), earthquakeApplicationService).start();
    }

    private static EarthquakeApplicationService setupApplicationService() {
        UsgsEarthquakeRestClient usgsEarthquakeRestClient = new UsgsEarthquakeRestClient(new EnvironmentProperties());
        EarthquakeRepository earthquakeRepository = new UsgsEarthquakeRepository(usgsEarthquakeRestClient);
        return new EarthquakeApplicationService(earthquakeRepository);
    }
}
