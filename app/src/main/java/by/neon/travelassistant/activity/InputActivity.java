package by.neon.travelassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.CategorySelectAsyncTask;
import by.neon.travelassistant.activity.query.TransportSelectAsyncTask;
import by.neon.travelassistant.adapter.SelectCityAdapter;
import by.neon.travelassistant.config.sqlite.mapper.CategoryMapper;
import by.neon.travelassistant.config.sqlite.mapper.TransportMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.listener.DateSetListener;
import by.neon.travelassistant.model.Category;
import by.neon.travelassistant.model.Transport;
import by.neon.travelassistant.model.Weather;

public class InputActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "InputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setTransport();
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
                break;
            case R.id.nav_about:
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

    public void onSelectStartDate(View view) {
        EditText text = findViewById(R.id.start_date);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        new DatePickerDialog(this, new DateSetListener(text),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onSelectEndDate(View view) {
        EditText text = findViewById(R.id.end_date);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        new DatePickerDialog(this, new DateSetListener(text),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onFindCity(View view) {
        EditText text = findViewById(R.id.arv_city);
        String cityName = text.getText().toString();
        createCitySelectDialog(cityName);
    }

    public void onSendClick(View view) {
        Intent intent = new Intent(InputActivity.this, PreviewActivity.class);
        intent.putExtra(CommonConstants.ARRIVAL_CITY_ID, (long) findViewById(R.id.arv_city).getTag());
        intent.putExtra(CommonConstants.TYPE_MALE, ((ToggleButton) findViewById(R.id.sex_male)).isChecked());
        intent.putExtra(CommonConstants.TYPE_FEMALE, ((ToggleButton) findViewById(R.id.sex_female)).isChecked());
        LinearLayout transports = findViewById(R.id.layout_transports);
        int countTransports = 0;
        for (int index = 0; index < transports.getChildCount(); index++) {
            LinearLayout layout = (LinearLayout) transports.getChildAt(index);
            for (int viewIndex = 0; viewIndex < layout.getChildCount(); viewIndex++) {
                ToggleButton button = (ToggleButton) layout.getChildAt(viewIndex);
                if (button.isChecked()) {
                    intent.putExtra("transport" + countTransports++, button.getText().toString());
                }
            }
        }
        intent.putExtra(CommonConstants.COUNT_TRANSPORT_TYPES, countTransports);
        LinearLayout layout = findViewById(R.id.layout_targets);
        int countTargets = 0;
        for (int layoutIndex = 0; layoutIndex < layout.getChildCount(); layoutIndex++) {
            LinearLayout inner = (LinearLayout) layout.getChildAt(layoutIndex);
            for (int viewIndex = 0; viewIndex < inner.getChildCount(); viewIndex++) {
                ToggleButton button = (ToggleButton) inner.getChildAt(viewIndex);
                if (button.isChecked()) {
                    intent.putExtra("type" + countTargets++, button.getHint().toString());
                }
            }
        }
        intent.putExtra(CommonConstants.COUNT_TARGETS, countTargets);
        startActivity(intent);
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
                    Weather weather = weatherList.get(which);
                    text.setText(String.format(Locale.getDefault(),"%s, %s (%d)",
                            weather.getCityName(),
                            weather.getCountryCode(),
                            weather.getCityId()));
                    text.setTag(weather.getCityId());
                })
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void setTravelTargets() {
        CategorySelectAsyncTask categorySelect = new CategorySelectAsyncTask();
        categorySelect.setSelectAll(true);
        CategoryMapper categoryMapper = new CategoryMapper();
        List<Category> targets = new ArrayList<>(0);
        try {
            targets = categoryMapper.to(categorySelect.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "setTravelTargets: " + e.getMessage(), e);
        }

        int index = 0;
        LinearLayout parent = findViewById(R.id.layout_targets);
        while (index < targets.size()) {
            LinearLayout innerLayout = new LinearLayout(this);
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.setMargins(0, 10, 0 ,0);
            innerLayout.setLayoutParams(layoutParams);
            for (int item = 0; item < 2 && index < targets.size(); item++, index++) {
                if (targets.get(index).getCategoryNameEn().equals("need")) {
                    continue;
                }

                ToggleButton button = (ToggleButton) getLayoutInflater().inflate(R.layout.toggle_button_style_layout, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMarginEnd((int) (getResources().getDimension(R.dimen.toggle_button_margin_end) / getResources().getDisplayMetrics().density));
                button.setPadding(5, 5, 5, 5);
                button.setLayoutParams(params);
                String localizedName = capitalize(targets.get(index).getCategoryName());
                button.setText(localizedName);
                button.setTextOn(localizedName);
                button.setTextOff(localizedName);
                button.setHint(targets.get(index).getCategoryNameEn());
                innerLayout.addView(button);
            }
            parent.addView(innerLayout);
        }
    }

    private void setTransport() {
        TransportSelectAsyncTask task = new TransportSelectAsyncTask();
        task.setSelectAll(true);
        TransportMapper mapper = new TransportMapper();
        List<Transport> transports = new ArrayList<>(0);
        try {
            transports = mapper.to(task.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "setTransport: " + e.getMessage(), e);
        }

        LinearLayout parent = findViewById(R.id.layout_transports);
        for (int index = 0; index < transports.size();) {
            LinearLayout inner = new LinearLayout(this);
            inner.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.setMargins(0, 10, 0 ,0);
            inner.setLayoutParams(layoutParams);
            for (int item = 0; item < 2 && index < transports.size(); item++, index++) {
                ToggleButton button = (ToggleButton) getLayoutInflater().inflate(R.layout.toggle_button_style_layout, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMarginEnd((int) (getResources().getDimension(R.dimen.toggle_button_margin_end) / getResources().getDisplayMetrics().density));
                button.setPadding(5, 5, 5, 5);
                button.setLayoutParams(params);
                String name = capitalize(transports.get(index).getName());
                button.setText(name);
                button.setTextOn(name);
                button.setTextOff(name);
                button.setHint(transports.get(index).getNameEn());
                inner.addView(button);
            }
            parent.addView(inner);
        }
    }

    private Drawable getTransportIcon(String transportName) {
        int id = 0;
        switch (transportName) {
            case "airplane": id = R.drawable.ic_airplane; break;
            case "bus": id = R.drawable.ic_bus; break;
            case "ship": id = R.drawable.ic_ship; break;
            case "cycle": id = R.drawable.ic_cycle; break;
            case "train": id = R.drawable.ic_train; break;
            case "auto": id = R.drawable.ic_auto; break;
        }
        return ContextCompat.getDrawable(this, id);
    }

    private String capitalize(String title) {
        return title.substring(0,1).toUpperCase() + title.substring(1);
    }
}
