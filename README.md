# nearby-earthquakes
Console application that for a given city coordinates (lat/lon) finds 10 most nearby earthquakes (earthquakes that happened in the closest distance of that city).

## Getting list of earthquakes
Information about earthquakes are fetched from : https://earthquake.usgs.gov/

# How to build and run application!
- Application to work requires Java 11 and Maven installed
- To be able to build it go to the folder with pom.xml and run command:
```sh
$ mvn clean install
```
- To run app execute command:
```sh
$ java -jar target/nearby-earthquakes-1.0-jar-with-dependencies.jar
```
# How to use app
After running app provide coordinates latitude and longitude separated by space character e.g:
```sh
50.049683 19.944544
```