package by.neon.travelassistant.config;

import android.app.Activity;
import android.location.Location;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class FlightStatsConfig extends Config {
    private Activity activity;
    private String appKey;
    private String appId;


    public FlightStatsConfig(Activity activity, String appId, String appKey) {
        this.activity = activity;
        this.appId = appId;
        this.appKey = appKey;
        this.setAirportsInfo(getAirports(null, 15));
    }

    private ArrayList<AirportInfo> getAirports(Location deviceLocation, int radiusMiles) {
        String url = new StringBuilder("https://api.flightstats.com/flex/airports/rest/v1/json/withinRadius/")
                .append(deviceLocation.getLongitude()).append('/')
                .append(deviceLocation.getLatitude()).append('/')
                .append(radiusMiles).append('?')
                .append("appId=").append(appId).append('&')
                .append("appKey=").append(appKey).toString();
        ArrayList<AirportInfo> list = new ArrayList<>(0);
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("airports");
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
                    } catch (JSONException e) {
                        activity.runOnUiThread(() -> Toast
                                .makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                                .show());
                    }
                }, null);
        queue.add(request);
        return list;
    }
}
