package by.neon.travelassistant.config;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class FlightStatsDemoConfig extends Config {
    private Activity activity;
    private JSONObject databaseDemo;


    public FlightStatsDemoConfig(Activity activity) {
        this.activity = activity;
        try {
            this.databaseDemo = new JSONObject(readDatabase(new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "database.json")));
        } catch (JSONException | IOException e) {
            Log.e(this.getClass().getName(), e.getLocalizedMessage());
        }
        this.setAirportsInfo(getAirports());
    }

    private ArrayList<AirportInfo> getAirports() {
        try {
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
                        .setLatitude(object.getDouble("latitude"))
                        .setLongitude(object.getDouble("longitude")));
            }
            return list;
        } catch (JSONException e) {
            activity.runOnUiThread(() -> Toast
                    .makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                    .show());
        }
        return null;
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
