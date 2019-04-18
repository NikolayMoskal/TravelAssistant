package by.neon.travelassistant;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockitoAnnotations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import by.neon.travelassistant.config.FlightStatsDemoConfig;

public class FlightStatsDemoConfigTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = FileNotFoundException.class)
    public void flightStatsDemoConfig_DirectoryNotExists() throws JSONException, IOException {
        new FlightStatsDemoConfig(new File(""), "database.json");
    }

    @Test(expected = FileNotFoundException.class)
    public void flightStatsDemoConfig_FileNotExists() throws JSONException, IOException {
        temporaryFolder.newFolder("storage", "0", "emulated", "Download");
        File file = temporaryFolder.newFile("storage/0/emulated/Download/database.json");
        new FlightStatsDemoConfig(new File(file.getParent()), "foo.bar");
    }

    @Test(expected = FileNotFoundException.class)
    public void flightStatsDemoConfig_BadFileExtension() throws JSONException, IOException {
        temporaryFolder.newFolder("storage", "0", "emulated", "Download");
        File file = temporaryFolder.newFile("storage/0/emulated/Download/database.json");
        new FlightStatsDemoConfig(new File(file.getParent()), "database.txt");
    }

    @Test
    public void flightStatsDemoConfig_CreateConfig() throws JSONException, IOException {
        temporaryFolder.newFolder("storage", "0", "emulated", "Download");
        File file = temporaryFolder.newFile("storage/0/emulated/Download/database.json");
        writeTestData(file);
        new FlightStatsDemoConfig(new File(file.getParent()), "database.json");
    }

    private void writeTestData(File file) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("{\n" +
                    "   \"airports\": [\n" +
                    "      {\n" +
                    "         \"fs\": \"GNA\",\n" +
                    "         \"iata\": \"GNA\",\n" +
                    "         \"icao\": \"UMMG\",\n" +
                    "         \"name\": \"Grodno Airport\",\n" +
                    "         \"city\": \"Grodno\",\n" +
                    "         \"cityCode\": \"GNA\",\n" +
                    "         \"countryCode\": \"BY\",\n" +
                    "         \"countryName\": \"Belarus\",\n" +
                    "         \"regionName\": \"Europe\",\n" +
                    "         \"timeZoneRegionName\": \"Europe/Minsk\",\n" +
                    "         \"localTime\": \"2018-12-10T14:09:55.62\",\n" +
                    "         \"utcOffsetHours\": 3,\n" +
                    "         \"latitude\": 53.6058,\n" +
                    "         \"longitude\": 24.053905,\n" +
                    "         \"elevationFeet\": 424,\n" +
                    "         \"classification\": 4,\n" +
                    "         \"active\": true,\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/GNA?codeType=fs\",\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/GNA?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"LAX\",\n" +
                    "         \"iata\": \"LAX\",\n" +
                    "         \"icao\": \"KLAX\",\n" +
                    "         \"faa\": \"LAX\",\n" +
                    "         \"name\": \"Los Angeles International Airport\",\n" +
                    "         \"street1\": \"One World Way\",\n" +
                    "         \"street2\": \"\",\n" +
                    "         \"city\": \"Los Angeles\",\n" +
                    "         \"cityCode\": \"LAX\",\n" +
                    "         \"stateCode\": \"CA\",\n" +
                    "         \"postalCode\": \"90045-5803\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Los_Angeles\",\n" +
                    "         \"weatherZone\": \"CAZ041\",\n" +
                    "         \"localTime\": \"2014-02-03T10:10:28.748\",\n" +
                    "         \"utcOffsetHours\": -8,\n" +
                    "         \"latitude\": 33.943398,\n" +
                    "         \"longitude\": -118.40828,\n" +
                    "         \"elevationFeet\": 126,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/LAX?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/LAX?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"SFO\",\n" +
                    "         \"iata\": \"SFO\",\n" +
                    "         \"icao\": \"KSFO\",\n" +
                    "         \"faa\": \"SFO\",\n" +
                    "         \"name\": \"San Francisco International Airport\",\n" +
                    "         \"city\": \"San Francisco\",\n" +
                    "         \"cityCode\": \"SFO\",\n" +
                    "         \"stateCode\": \"CA\",\n" +
                    "         \"postalCode\": \"94128\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Los_Angeles\",\n" +
                    "         \"weatherZone\": \"CAZ508\",\n" +
                    "         \"localTime\": \"2014-02-03T10:10:28.760\",\n" +
                    "         \"utcOffsetHours\": -8,\n" +
                    "         \"latitude\": 37.615215,\n" +
                    "         \"longitude\": -122.38988,\n" +
                    "         \"elevationFeet\": 11,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/SFO?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/SFO?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"DEN\",\n" +
                    "         \"iata\": \"DEN\",\n" +
                    "         \"icao\": \"KDEN\",\n" +
                    "         \"faa\": \"DEN\",\n" +
                    "         \"name\": \"Denver International Airport\",\n" +
                    "         \"street1\": \"8500 Pena Boulevard\",\n" +
                    "         \"city\": \"Denver\",\n" +
                    "         \"cityCode\": \"DEN\",\n" +
                    "         \"stateCode\": \"CO\",\n" +
                    "         \"postalCode\": \"80249-6340\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Denver\",\n" +
                    "         \"weatherZone\": \"COZ040\",\n" +
                    "         \"localTime\": \"2014-02-03T11:10:28.742\",\n" +
                    "         \"utcOffsetHours\": -7,\n" +
                    "         \"latitude\": 39.851383,\n" +
                    "         \"longitude\": -104.673096,\n" +
                    "         \"elevationFeet\": 5389,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/DEN?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/DEN?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"PHX\",\n" +
                    "         \"iata\": \"PHX\",\n" +
                    "         \"icao\": \"KPHX\",\n" +
                    "         \"faa\": \"PHX\",\n" +
                    "         \"name\": \"Phoenix Sky Harbor International Airport\",\n" +
                    "         \"street1\": \"3400 Sky Harbor Boulevard\",\n" +
                    "         \"city\": \"Phoenix\",\n" +
                    "         \"cityCode\": \"PHX\",\n" +
                    "         \"stateCode\": \"AZ\",\n" +
                    "         \"postalCode\": \"85034\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Phoenix\",\n" +
                    "         \"weatherZone\": \"AZZ023\",\n" +
                    "         \"localTime\": \"2014-02-03T11:10:28.754\",\n" +
                    "         \"utcOffsetHours\": -7,\n" +
                    "         \"latitude\": 33.435036,\n" +
                    "         \"longitude\": -112.00016,\n" +
                    "         \"elevationFeet\": 1135,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/PHX?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/PHX?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"PDX\",\n" +
                    "         \"iata\": \"PDX\",\n" +
                    "         \"icao\": \"KPDX\",\n" +
                    "         \"faa\": \"PDX\",\n" +
                    "         \"name\": \"Portland International Airport\",\n" +
                    "         \"street1\": \"7000 NE Airport Way\",\n" +
                    "         \"city\": \"Portland\",\n" +
                    "         \"cityCode\": \"PDX\",\n" +
                    "         \"stateCode\": \"OR\",\n" +
                    "         \"postalCode\": \"97218\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Los_Angeles\",\n" +
                    "         \"weatherZone\": \"ORZ006\",\n" +
                    "         \"localTime\": \"2014-02-03T10:10:28.723\",\n" +
                    "         \"utcOffsetHours\": -8,\n" +
                    "         \"latitude\": 45.588997,\n" +
                    "         \"longitude\": -122.5929,\n" +
                    "         \"elevationFeet\": 30,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/PDX?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/PDX?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"LIH\",\n" +
                    "         \"iata\": \"LIH\",\n" +
                    "         \"icao\": \"PHLI\",\n" +
                    "         \"faa\": \"PHLI\",\n" +
                    "         \"name\": \"Lihue Airport\",\n" +
                    "         \"street1\": \"3901 Mokulele Lap, Box 6\",\n" +
                    "         \"city\": \"Kauai Island\",\n" +
                    "         \"cityCode\": \"LIH\",\n" +
                    "         \"stateCode\": \"HI\",\n" +
                    "         \"postalCode\": \"96766\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"Pacific/Honolulu\",\n" +
                    "         \"weatherZone\": \"HIZ001\",\n" +
                    "         \"localTime\": \"2014-02-03T08:10:28.750\",\n" +
                    "         \"utcOffsetHours\": -10,\n" +
                    "         \"latitude\": 21.978205,\n" +
                    "         \"longitude\": -159.34944,\n" +
                    "         \"elevationFeet\": 153,\n" +
                    "         \"classification\": 3,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/LIH?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/LIH?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"DFW\",\n" +
                    "         \"iata\": \"DFW\",\n" +
                    "         \"icao\": \"KDFW\",\n" +
                    "         \"faa\": \"DFW\",\n" +
                    "         \"name\": \"Dallas/Fort Worth International Airport\",\n" +
                    "         \"street1\": \"3200 East Airfield Drive\",\n" +
                    "         \"city\": \"Dallas\",\n" +
                    "         \"cityCode\": \"DFW\",\n" +
                    "         \"stateCode\": \"TX\",\n" +
                    "         \"postalCode\": \"75261\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Chicago\",\n" +
                    "         \"weatherZone\": \"TXZ119\",\n" +
                    "         \"localTime\": \"2014-02-03T12:10:28.746\",\n" +
                    "         \"utcOffsetHours\": -6,\n" +
                    "         \"latitude\": 32.89746,\n" +
                    "         \"longitude\": -97.036125,\n" +
                    "         \"elevationFeet\": 603,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/DFW?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/DFW?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"SEA\",\n" +
                    "         \"iata\": \"SEA\",\n" +
                    "         \"icao\": \"KSEA\",\n" +
                    "         \"faa\": \"SEA\",\n" +
                    "         \"name\": \"Seattle-Tacoma International Airport\",\n" +
                    "         \"city\": \"Seattle\",\n" +
                    "         \"cityCode\": \"SEA\",\n" +
                    "         \"stateCode\": \"WA\",\n" +
                    "         \"postalCode\": \"98158\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Los_Angeles\",\n" +
                    "         \"weatherZone\": \"WAZ001\",\n" +
                    "         \"localTime\": \"2014-02-03T10:10:28.756\",\n" +
                    "         \"utcOffsetHours\": -8,\n" +
                    "         \"latitude\": 47.44384,\n" +
                    "         \"longitude\": -122.301735,\n" +
                    "         \"elevationFeet\": 429,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/SEA?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/SEA?codeType=fs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"fs\": \"ORD\",\n" +
                    "         \"iata\": \"ORD\",\n" +
                    "         \"icao\": \"KORD\",\n" +
                    "         \"faa\": \"ORD\",\n" +
                    "         \"name\": \"O'Hare International Airport\",\n" +
                    "         \"street1\": \"10000 West O'Hare\",\n" +
                    "         \"city\": \"Chicago\",\n" +
                    "         \"cityCode\": \"CHI\",\n" +
                    "         \"stateCode\": \"IL\",\n" +
                    "         \"postalCode\": \"60666\",\n" +
                    "         \"countryCode\": \"US\",\n" +
                    "         \"countryName\": \"United States\",\n" +
                    "         \"regionName\": \"North America\",\n" +
                    "         \"timeZoneRegionName\": \"America/Chicago\",\n" +
                    "         \"weatherZone\": \"ILZ014\",\n" +
                    "         \"localTime\": \"2014-02-03T12:10:28.752\",\n" +
                    "         \"utcOffsetHours\": -6,\n" +
                    "         \"latitude\": 41.976913,\n" +
                    "         \"longitude\": -87.90488,\n" +
                    "         \"elevationFeet\": 668,\n" +
                    "         \"classification\": 1,\n" +
                    "         \"active\": true,\n" +
                    "         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/ORD?codeType=fs\",\n" +
                    "         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/ORD?codeType=fs\"\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}");
        }
    }
}
