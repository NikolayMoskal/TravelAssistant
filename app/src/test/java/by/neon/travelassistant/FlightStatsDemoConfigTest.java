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
            writer.write("{\r\n   \"airports\": [\r\n      {\r\n         \"fs\": \"GNA\",\r\n         \"iata\": \"GNA\",\r\n         \"icao\": \"UMMG\",\r\n         \"name\": \"Grodno Airport\",\r\n         \"city\": \"Grodno\",\r\n         \"cityCode\": \"GNA\",\r\n         \"countryCode\": \"BY\",\r\n         \"countryName\": \"Belarus\",\r\n         \"regionName\": \"Europe\",\r\n         \"timeZoneRegionName\": \"Europe/Minsk\",\r\n         \"localTime\": \"2018-12-10T14:09:55.62\",\r\n         \"utcOffsetHours\": 3,\r\n         \"latitude\": 53.6058,\r\n         \"longitude\": 24.053905,\r\n         \"elevationFeet\": 424,\r\n         \"classification\": 4,\r\n         \"active\": true,\r\n         \"weatherUrl\": \"https://api.flightstats.com/flex/weather/rest/v1/json/all/GNA?codeType=fs\",\r\n         \"delayIndexUrl\": \"https://api.flightstats.com/flex/delayindex/rest/v1/json/airports/GNA?codeType=fs\"\r\n      }\r\n   ]\r\n}");
        }
    }
}
