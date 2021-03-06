package by.neon.travelassistant.listener;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.neon.travelassistant.model.Weather;

/**
 * Handles the weather forecast response from OpenWeatherMap server.
 */
public class ForecastListener implements Response.Listener<JSONObject> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "ForecastListener";
    /**
     * The callback to return the parsed response.
     */
    private ForecastCallback callback;

    /**
     * Builds a new instance of {@link ForecastListener} using a callback for return the forecast response.
     *
     * @param callback the callback to return the parsed response.
     */
    public ForecastListener(ForecastCallback callback) {
        this.callback = callback;
    }

    /**
     * Called when a response is received.
     *
     * @param response received response
     */
    @Override
    public void onResponse(JSONObject response) {
        try {
            List<Weather> weatherList = parseJsonResponse(response);
            callback.onSuccess(weatherList);
        } catch (JSONException e) {
            Log.e(TAG, "onResponse: " + e.getMessage(), e);
        }
    }

    /**
     * Parses the JSON response that contains the weather forecast snapshots.
     *
     * @param response the JSON response with forecast data.
     * @return the list of weather forecast snapshots.
     * @throws JSONException then the JSON is invalid.
     */
    private List<Weather> parseJsonResponse(JSONObject response) throws JSONException {
        JSONArray list = response.getJSONArray("list");
        JSONObject city = response.getJSONObject("city");
        List<Weather> weatherList = new ArrayList<>(0);
        for (int snapshotIndex = 0; snapshotIndex < list.length(); snapshotIndex++) {
            Weather weather = new Weather();
            JSONObject snapshot = list.getJSONObject(snapshotIndex);
            JSONArray weatherConditionStates = snapshot.getJSONArray("weather");
            JSONObject mainInfo = snapshot.getJSONObject("main");
            JSONObject coordinates = city.getJSONObject("coord");

            weather.setCityId(city.getLong("id"));
            weather.setCountryCode(city.getString("country"));
            weather.setCityName(city.getString("name"));
            weather.setCalculationDate(new Date(snapshot.getLong("dt")));
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(coordinates.getDouble("lat"));
            location.setLongitude(coordinates.getDouble("lon"));
            weather.setLocation(location);
            weather.setCurrentTemp(mainInfo.getDouble("temp"));
            weather.setMaxTemp(mainInfo.getDouble("temp_max"));
            weather.setMinTemp(mainInfo.getDouble("temp_min"));
            if (!snapshot.isNull("rain") && !snapshot.getJSONObject("rain").isNull("3h")) {
                weather.setRainVolume(snapshot.getJSONObject("rain").getDouble("3h"));
            }
            if (!snapshot.isNull("snow") && !snapshot.getJSONObject("snow").isNull("3h")) {
                weather.setSnowVolume(snapshot.getJSONObject("snow").getDouble("3h"));
            }
            List<Weather.State> states = new ArrayList<>();
            for (int index = 0; index < weatherConditionStates.length(); index++) {
                JSONObject object = weatherConditionStates.getJSONObject(index);
                Weather.State state = new Weather.State();
                state.setTitle(object.getString("description"));
                state.setIconUrl("http://openweathermap.org/img/w/" + object.getString("icon") + ".png");
                states.add(state);
            }
            weather.setStates(states);
            weatherList.add(weather);
        }

        return weatherList;
    }
}
