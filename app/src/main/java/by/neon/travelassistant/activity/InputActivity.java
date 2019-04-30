package by.neon.travelassistant.activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import by.neon.travelassistant.R;
import by.neon.travelassistant.adapters.SelectCityAdapter;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.OwmConfig;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Weather;

public class InputActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "InputActivity";
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.travel_data_label);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        config = new OwmConfig(this);
        setTravelTargets();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                break;
            case R.id.nav_informer:
                // TODO add link to InformerActivity
                break;
            case R.id.nav_about:
                // TODO show about
                break;
            case R.id.nav_manage:
                Intent intent = new Intent(InputActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        return new SelectCityAdapter(this, maps, R.layout.select_city_layout, keys, ids);
    }

    public void createCitySelectDialog(String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/find?" +
                "q=" + cityName +
                "&appid=" + CommonConstants.OWM_APP_ID +
                "&lang=" + Locale.getDefault().getLanguage();
        RequestQueue queue = Volley.newRequestQueue(InputActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        List<Weather> weatherList = parseJsonResponse(response);
                        showCitySelectDialog(weatherList, configureCitySelectAdapter(weatherList));
                    } catch (JSONException e) {
                        VolleyLog.e(e, "%s", e.getMessage());
                    }
                }, null);
        queue.add(request);
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
            weather.setCurrentTemp(mainInfo.getDouble("temp") - 273.15);
            weather.setCalculationDate(new Date(info.getLong("dt")));
            weather.setMaxTemp(mainInfo.getDouble("temp_max") - 273.15);
            weather.setMinTemp(mainInfo.getDouble("temp_min") - 273.15);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Выберите город")
                .setCancelable(true)
                .setAdapter(adapter, (dialog, which) -> {
                    EditText text = findViewById(R.id.arv_city);
                    text.setText(String.valueOf(weatherList.get(which).getCityId()));
                })
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void setTravelTargets() {
        LinearLayout parent = findViewById(R.id.layout_targets);
        String[] targets = getResources().getStringArray(R.array.targets);

        int index = 0;
        while (index < targets.length) {
            LinearLayout innerLayout = new LinearLayout(this);
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.setMargins(0, 10, 0 ,0);
            innerLayout.setLayoutParams(layoutParams);
            for (int item = 0; item < 3 && index < targets.length; item++, index++) {
                ToggleButton button = (ToggleButton) getLayoutInflater().inflate(R.layout.toggle_button_style_layout, null);
                button.setId(View.generateViewId());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMarginEnd((int) (getResources().getDimension(R.dimen.toggle_button_margin_end) / getResources().getDisplayMetrics().density));
                button.setPadding(5, 5, 5, 5);
                button.setLayoutParams(params);
                button.setText(targets[index]);
                button.setTextOn(targets[index]);
                button.setTextOff(targets[index]);
                innerLayout.addView(button);
            }
            parent.addView(innerLayout);
        }
    }

    public void onFindCity(View view) {
        EditText text = findViewById(R.id.arv_city);
        String cityName = text.getText().toString();
        createCitySelectDialog(cityName);
    }

    public void onSendClick(View view) {
        Intent intent = new Intent(InputActivity.this, PackActivity.class);
        startActivity(intent);
    }
}
