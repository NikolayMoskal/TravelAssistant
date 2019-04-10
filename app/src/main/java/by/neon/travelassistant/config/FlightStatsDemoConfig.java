package by.neon.travelassistant.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class FlightStatsDemoConfig extends Config {
    private JSONObject databaseDemo;


    public FlightStatsDemoConfig(File directory, String databaseName) throws JSONException, IOException {
        this.databaseDemo = createDatabase(directory, databaseName);
        this.setAirportsInfo(getAirports());
    }

    private ArrayList<AirportInfo> getAirports() throws JSONException {
        ArrayList<AirportInfo> list = new ArrayList<>(0);
        JSONArray array = databaseDemo.getJSONArray("airports");
        for (int index = 0; index < array.length(); index++) {
            JSONObject object = array.getJSONObject(index);
            list.add(new AirportInfo()
                    .setIataCode(object.getString("iata"))
                    .setIcaoCode(object.getString("icao"))
                    .setCityName(object.getString("city"))
                    .setAirportName(object.getString("name"))
                    .setCountryName(object.getString("countryName"))
                    .setCountryCode(object.getString("countryCode"))
                    .setLatitude(object.getDouble("latitude"))
                    .setLongitude(object.getDouble("longitude")));
        }
        return list;
    }

    private JSONObject createDatabase(File directory, String databaseName) throws JSONException, IOException {
        File file = new File(directory, databaseName);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("Incorrect database file name");
        }
        return new JSONObject(readDatabase(file));
    }

    private String readDatabase(File database) throws IOException {
        StringBuilder text = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(database));
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line);
            text.append('\n');
        }
        br.close();
        return text.toString();
    }


}
