package by.neon.travelassistant.listener;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import by.neon.travelassistant.R;
import by.neon.travelassistant.adapter.SelectCityAdapter;
import by.neon.travelassistant.model.Weather;

public class CitySelectListener implements Response.Listener<JSONObject> {
    private static final String TAG = "CityWeatherListener";
    private Activity activity;

    public CitySelectListener(Activity activity) {
        this.activity = activity;
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
            showCitySelectDialog(weatherList, configureCitySelectAdapter(weatherList));
        } catch (JSONException e) {
            Log.e(TAG, "onResponse: " + e.getMessage(), e);
        }
    }

    private List<Weather> parseJsonResponse(JSONObject response) throws JSONException {
        JSONArray responses = response.getJSONArray("list");
        List<Weather> weatherList = new ArrayList<>();
        for (int cityIndex = 0; cityIndex < responses.length(); cityIndex++) {
            Weather weather = new Weather();
            JSONObject info = responses.getJSONObject(cityIndex);
            JSONArray weatherConditionStates = info.getJSONArray("weather");
            JSONObject mainInfo = info.getJSONObject("main");
            JSONObject coordinates = info.getJSONObject("coord");
            JSONObject systemInfo = info.getJSONObject("sys");

            weather.setCityId(info.getLong("id"));
            weather.setCityName(info.getString("name"));
            weather.setCountryCode(systemInfo.getString("country"));
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(coordinates.getDouble("lat"));
            location.setLongitude(coordinates.getDouble("lon"));
            weather.setLocation(location);
            weather.setCurrentTemp(mainInfo.getDouble("temp"));
            weather.setCalculationDate(new Date(info.getLong("dt")));
            weather.setMaxTemp(mainInfo.getDouble("temp_max"));
            weather.setMinTemp(mainInfo.getDouble("temp_min"));
            if (!info.isNull("rain") && !info.getJSONObject("rain").isNull("1h")) {
                weather.setRainVolume(info.getJSONObject("rain").getDouble("1h"));
            }
            if (!info.isNull("snow") && !info.getJSONObject("snow").isNull("1h")) {
                weather.setSnowVolume(info.getJSONObject("snow").getDouble("1h"));
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

    private void showCitySelectDialog(List<Weather> weatherList, ListAdapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle(R.string.city_select_title)
                .setCancelable(true)
                .setAdapter(adapter, (dialog, which) -> {
                    EditText text = activity.findViewById(R.id.arv_city);
                    Weather weather = weatherList.get(which);
                    text.setText(String.format(Locale.getDefault(), "%s, %s (%d)",
                            weather.getCityName(),
                            weather.getCountryCode(),
                            weather.getCityId()));
                    text.setTag(weather.getCityId());
                })
                .setNegativeButton(R.string.action_cancel, null);
        builder.create().show();
    }

    private SimpleAdapter configureCitySelectAdapter(List<Weather> weatherList) {
        String[] keys = new String[]{"Icon", "City", "Location", "Temp", "Selected"};
        ArrayList<HashMap<String, String>> maps = new ArrayList<>(0);
        for (Weather weather : weatherList) {
            HashMap<String, String> map = new HashMap<>();
            map.put(keys[0], weather.getStates().get(0).getIconUrl());
            map.put(keys[1], String.format("%s, %s", weather.getCityName(), weather.getCountryCode()));
            map.put(keys[2], String.format("Geo coords: [%s,%s]", weather.getLocation().getLatitude(), weather.getLocation().getLongitude()));
            map.put(keys[3], String.format("%s ºC", weather.getCurrentTemp()));
            map.put(keys[4], weather.getCityName());
            maps.add(map);
        }
        int[] ids = new int[]{R.id.citySelectWeatherIcon, R.id.citySelectCityNameAndCountry, R.id.citySelectCityCoordinates, R.id.citySelectCurrentTemp, R.id.selectedCityName};
        return new SelectCityAdapter(activity, maps, R.layout.select_city_layout, keys, ids);
    }
}
