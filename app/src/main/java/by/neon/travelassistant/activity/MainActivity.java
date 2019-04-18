package by.neon.travelassistant.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

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
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.FlightStatsDemoConfig;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.config.sqlite.mapper.AirportMapper;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.config.sqlite.model.CountryDb;
import by.neon.travelassistant.constant.DialogConstants;
import by.neon.travelassistant.constant.GpsLocationConstants;
import by.neon.travelassistant.constant.RuntimePermissionConstants;
import by.neon.travelassistant.listener.AutoCompleteTextViewItemClickListener;
import by.neon.travelassistant.listener.CustomLocationListener;
import by.neon.travelassistant.model.Airport;

/**
 * Represents the main window of the TravelAssistant application
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private LocationManager locationManager;
    private Config config;
    private LocationListener locationListener;
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new CustomLocationListener(this);

        // TODO add progress bar to all tasks
        try {
            airportsInDatabase = new AirportSelectAsyncTask().execute().get().size();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }
        try {
            configureAirportsDatabase();
            configureArrivalAirportView();
            configureDepartureAirportView();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
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
        AutoCompleteTextView textView = findViewById(R.id.dep_airport_improved);
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
                // TODO include some settings if necessary
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
    }

    private long checkExistingCountry(CountryDb countryDb) throws ExecutionException, InterruptedException {
        List<CountryDb> foundCountries = new CountrySelectAsyncTask(countryDb.getCountryName()).execute().get();
        return foundCountries.size() > 0
                ? foundCountries.get(0).getId()
                : new CountryInsertAsyncTask().execute(countryDb).get().get(0);
    }

    /**
     * Shows the dialog to select the airport from the list.
     *
     * @param view the sender view
     */
    @SuppressWarnings("deprecation")
    public void onChoice(View view) {
        showDialog(DialogConstants.CHOICE_DEP_AIRPORT_DIALOG);
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
        // TODO remove this if will be used autocomplete view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DialogConstants.CHOICE_DEP_AIRPORT_DIALOG:
                builder.setTitle("Выберите аэропорт"); // заголовок для диалога
                ArrayList<String> strings = new ArrayList<>(0);
                ArrayList<Airport> airports = config.getAirportsInfo();
                for (Airport info : airports) {
                    strings.add(info.getAirportName());
                }
                builder.setItems(strings.toArray(new String[0]), (dialog, item) -> {
                    EditText text = findViewById(R.id.dep_airport);
                    text.setText(airports.get(item).getIataCode());
                });
            case DialogConstants.CHOICE_ARV_AIRPORT_DIALOG:
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
                "Коды аэропортов указываются в международном коде IATA. Пример: MSQ - Национальный аэропорт Минск",
                Toast.LENGTH_LONG).show());
    }

    /**
     * Sends the user data to next window to show recommended things.
     *
     * @param view the sender view
     */
    public void onSendClick(View view) {
        locationManager.removeUpdates(locationListener);
    }

    /**
     * Checks the need permissions to use the GPS or network connection and runs the location updates.
     *
     * @param view the sender view
     */
    public void onLocationClick(View view) {
        if (!checkLocationProvider()) {
            showLocationSettingsDialog();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RuntimePermissionConstants.ACCESS_FINE_LOCATION_PERMISSION);
        } else {
            Log.i(TAG, "onLocationClick: GPS permission is granted");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GpsLocationConstants.MIN_TIME_UPDATE_MSEC, GpsLocationConstants.MIN_DISTANCE_MILES, locationListener);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, RuntimePermissionConstants.ACCESS_COARSE_LOCATION_PERMISSION);
        } else {
            Log.i(TAG, "onLocationClick: Network permission is granted");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GpsLocationConstants.MIN_TIME_UPDATE_MSEC, GpsLocationConstants.MIN_DISTANCE_MILES, locationListener);
        }
    }

    /**
     * Checks the GPS and network providers.
     *
     * @return <b>true</b> if any provider is enabled and <b>false</b> if otherwise
     */
    private boolean checkLocationProvider() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showLocationSettingsDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease allow to receive your location to work this app properly.")
                .setPositiveButton("Location Settings", (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
                });
        dialog.show();
    }

    /**
     * {@inheritDoc}
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RuntimePermissionConstants.ACCESS_FINE_LOCATION_PERMISSION:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GpsLocationConstants.MIN_TIME_UPDATE_MSEC, GpsLocationConstants.MIN_DISTANCE_MILES, locationListener);
                }
                Log.i(TAG, "onRequestPermissionsResult: Listen location updates via GPS");
                break;
            case RuntimePermissionConstants.ACCESS_COARSE_LOCATION_PERMISSION:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GpsLocationConstants.MIN_TIME_UPDATE_MSEC, GpsLocationConstants.MIN_DISTANCE_MILES, locationListener);
                }
                Log.i(TAG, "onRequestPermissionsResult: Listen location updates via network");
                break;
        }
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
                "Введите первые буквы названия аэропорта, а затем выберите его из списка найденных",
                Toast.LENGTH_LONG).show());
    }
}
