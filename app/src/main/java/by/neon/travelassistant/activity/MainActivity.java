package by.neon.travelassistant.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.AirportInsertAsyncTask;
import by.neon.travelassistant.activity.query.AirportSelectAsyncTask;
import by.neon.travelassistant.activity.query.CityInsertAsyncTask;
import by.neon.travelassistant.activity.query.CountryInsertAsyncTask;
import by.neon.travelassistant.activity.query.CountrySelectAsyncTask;
import by.neon.travelassistant.activity.query.ThingInsertAsyncTask;
import by.neon.travelassistant.activity.query.ThingSelectAsyncTask;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.FlightStatsDemoConfig;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.config.sqlite.mapper.AirportMapper;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.config.sqlite.model.CountryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.constant.DialogConstants;
import by.neon.travelassistant.listener.AutoCompleteTextViewItemClickListener;
import by.neon.travelassistant.model.Airport;
import by.neon.travelassistant.model.Thing;

/**
 * Represents the main window of the TravelAssistant application
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private Config config;
    private int airportsInDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(R.string.travel_data_label);
        setSupportActionBar(toolbar);

        configureDrawerLayout(toolbar);
        configureNavigationView();

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        // TODO add progress bar to all tasks
        try {
            airportsInDatabase = new AirportSelectAsyncTask().execute().get().size();
            if (!existsThingsInDatabase()) {
                saveThingsToDatabase();
            }
            configureAirportsDatabase();
            configureArrivalAirportView();
            configureDepartureAirportView();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (preferences.contains(CommonConstants.PREF_DEP_AIRPORT_ID) &&
                preferences.contains(CommonConstants.PREF_ARV_AIRPORT_ID)) {
            //noinspection deprecation
            showDialog(DialogConstants.RESTORE_AIRPORTS_DIALOG);
        }
    }

    /**
     * Configures the view for arrival airport
     */
    private void configureArrivalAirportView() {
        AutoCompleteTextView textView = findViewById(R.id.arv_airport);
        textView.setAdapter(configureAdapter());
        textView.setOnItemClickListener(new AutoCompleteTextViewItemClickListener(this));
    }

    /**
     * Configures the view for departure airport
     */
    private void configureDepartureAirportView() {
        AutoCompleteTextView textView = findViewById(R.id.dep_airport);
        textView.setAdapter(configureAdapter());
        textView.setOnItemClickListener(new AutoCompleteTextViewItemClickListener(this));
    }

    /**
     * Configures the navigation view
     */
    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean existsThingsInDatabase() throws ExecutionException, InterruptedException {
        return new ThingSelectAsyncTask().execute().get().size() > 0;
    }

    private void saveThingsToDatabase() throws IOException, ExecutionException, InterruptedException {
        String array = readJsonFromFile();
        ObjectMapper mapper = new ObjectMapper();
        List<Thing> things = mapper.readValue(array, new TypeReference<List<Thing>>(){});
        ThingInsertAsyncTask task = new ThingInsertAsyncTask();
        ThingMapper thingMapper = new ThingMapper();
        task.execute(thingMapper.from(things).toArray(new ThingDb[things.size()])).get();
    }

    public String readJsonFromFile() throws IOException {
        Writer writer = new StringWriter();
        try (InputStream stream = getResources().openRawResource(R.raw.all_things);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }

        return writer.toString();
    }

    private void saveLastUsedAirports(long departureAirportId, long arrivalAirportId) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(CommonConstants.PREF_DEP_AIRPORT_ID, departureAirportId);
        editor.putLong(CommonConstants.PREF_ARV_AIRPORT_ID, arrivalAirportId);
        editor.apply();
    }

    private void readLastUsedAirports() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        List<Airport> airports = config.getAirportsInfo();

        AutoCompleteTextView depAirportView = findViewById(R.id.dep_airport);
        depAirportView.setText(single(airports, preferences.getLong(CommonConstants.PREF_DEP_AIRPORT_ID, 0)).getIataCode());
        AutoCompleteTextView arvAirportView = findViewById(R.id.arv_airport);
        arvAirportView.setText(single(airports, preferences.getLong(CommonConstants.PREF_ARV_AIRPORT_ID, 0)).getIataCode());
    }

    private Airport single(List<Airport> list, long id) {
        for (Airport airport : list) {
            if (airport.getId() == id) {
                return airport;
            }
        }
        return list.get(0);
    }

    private Airport single(List<Airport> list, String iataCode) {
        for (Airport airport : list) {
            if (airport.getIataCode().equals(iataCode)) {
                return airport;
            }
        }
        return list.get(0);
    }

    /**
     * Configures the drawer layout
     *
     * @param toolbar the application toolbar
     */
    private void configureDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                break;
            case R.id.nav_things:
                // TODO add link to PackActivity (name can be improved)
                break;
            case R.id.nav_informer:
                // TODO add link to InformerActivity
                break;
            case R.id.nav_about:
                // TODO show about
                break;
            case R.id.nav_manage:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void finish() {
        if (config instanceof SqliteConfig) {
            return;
        }

        long depAirportId = single(config.getAirportsInfo(), ((AutoCompleteTextView) findViewById(R.id.dep_airport)).getText().toString()).getId();
        long arvAirportId = single(config.getAirportsInfo(), ((AutoCompleteTextView) findViewById(R.id.arv_airport)).getText().toString()).getId();
        saveLastUsedAirports(depAirportId, arvAirportId);

        try {
            AirportMapper mapper = new AirportMapper();
            List<AirportDb> airportDbs = mapper.from(config.getAirportsInfo());
            for (AirportDb airportDb : airportDbs) {
                CountryDb countryDb = airportDb.getCityDb().getCountryDb();
                CityDb cityDb = airportDb.getCityDb();

                cityDb.setCountryId(checkExistingCountry(countryDb));
                long cityId = new CityInsertAsyncTask().execute(cityDb).get().get(0);
                airportDb.setCityId(cityId);
                new AirportInsertAsyncTask().execute(airportDb).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "finish: " + e.getMessage(), e);
        }
        super.finish();
    }

    private long checkExistingCountry(CountryDb countryDb) throws ExecutionException, InterruptedException {
        List<CountryDb> foundCountries = new CountrySelectAsyncTask(countryDb.getCountryName()).execute().get();
        return foundCountries.size() > 0
                ? foundCountries.get(0).getId()
                : new CountryInsertAsyncTask().execute(countryDb).get().get(0);
    }

    /**
     * {@inheritDoc}
     *
     * @param id
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DialogConstants.RESTORE_AIRPORTS_DIALOG:
                builder.setMessage(R.string.last_used_airports_dialog_msg); // заголовок для диалога
                builder.setPositiveButton(R.string.ok_button, (dialog, which) -> readLastUsedAirports());
                break;
            default:
                break;
        }
        builder.setCancelable(true);
        return builder.create();
    }

    /**
     * Shows the short info to input the airport codes.
     *
     * @param view the sender view
     */
    public void onInfoClick(View view) {
        runOnUiThread(() -> Toast.makeText(
                getApplicationContext(),
                R.string.info_toast_title,
                Toast.LENGTH_LONG).show());
    }

    /**
     * Sends the user data to next window to show recommended things.
     *
     * @param view the sender view
     */
    public void onSendClick(View view) {
    }

    private SimpleAdapter configureAdapter() {
        String[] keys = new String[]{"AirportInfo", "AirportCode"};
        ArrayList<HashMap<String, String>> maps = new ArrayList<>(0);
        for (Airport info : config.getAirportsInfo()) {
            HashMap<String, String> map = new HashMap<>(0);
            map.put(keys[0], String.format("%s, %s, %s (%s)", info.getCity().getCountry().getCountryName(), info.getCity().getCityName(), info.getAirportName(), info.getIataCode()));
            map.put(keys[1], info.getIataCode());
            maps.add(map);
        }
        int[] ids = new int[]{R.id.airportInfo, R.id.airportCode};
        return new SimpleAdapter(this, maps, R.layout.airport_list_item, keys, ids);
    }

    private void configureAirportsDatabase() throws Exception {
        if (airportsInDatabase > 0) {
            config = new SqliteConfig();
            Log.i(TAG, "configureAirportsDatabase: Use internal database.");
        } else {
            config = new FlightStatsDemoConfig();
            Log.i(TAG, "configureAirportsDatabase: Use demo database.");
        }
    }

    /**
     * Shows help for search the airport
     *
     * @param view the sender view
     */
    public void onAirportHelp(View view) {
        runOnUiThread(() -> Toast.makeText(
                getApplicationContext(),
                R.string.airport_help_toast_title,
                Toast.LENGTH_LONG).show());
    }
}
