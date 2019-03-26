package by.neon.travelassistant;

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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.util.ArrayList;

import by.neon.travelassistant.config.AirportInfo;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.FlightStatsDemoConfig;
import by.neon.travelassistant.constants.LogTag;

/**
 * Represents the main window of the TravelAssistant application
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private LocationManager locationManager;
    private Config config;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new CustomLocationListener();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            config = new FlightStatsDemoConfig(this);
        }
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
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_things:

                break;
            case R.id.nav_informer:

                break;
            case R.id.nav_about:

                break;
            case R.id.nav_manage:

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Shows the dialog to select the airport from the list.
     * @param view the sender view
     */
    @SuppressWarnings("deprecation")
    public void onChoice(View view) {
        showDialog(0);
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите аэропорт"); // заголовок для диалога
        ArrayList<String> strings = new ArrayList<>(0);
        for (AirportInfo info : config.getAirportsInfo()) {
            strings.add(info.getAirportName());
        }
        builder.setItems(strings.toArray(new String[0]), (dialog, item) -> {
            EditText text = findViewById(R.id.dep_airport);
            text.setText(config.getAirportsInfo().get(item).getIataCode());
        });
        builder.setCancelable(true);
        return builder.create();
    }

    /**
     * Shows the short info to input the airport codes.
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
     * @param view the sender view
     */
    public void onSendClick(View view) {
        locationManager.removeUpdates(locationListener);
    }

    /**
     * Checks the need permissions to use the GPS or network connection and runs the location updates.
     * @param view the sender view
     */
    public void onLocationClick(View view) {
        if (!checkLocationProvider()) {
            showLocationSettingsDialog();
            return;
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.i(TAG, "onLocationClick: GPS permission is granted");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            } else {
                Log.i(TAG, "onLocationClick: Network permission is granted");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
            }
        } catch (Exception e) {
            Log.e(LogTag.LOG_TAG_GPS, e.getMessage(), e);
        }
    }

    /**
     * Checks the GPS and network providers.
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
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                config = new FlightStatsDemoConfig(this);
                Log.i(TAG, "onRequestPermissionsResult: Use FlightStats demo database");
                break;
            case 1:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                }
                Log.i(TAG, "onRequestPermissionsResult: Listen location updates via GPS");
                break;
            case 2:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
                }
                Log.i(TAG, "onRequestPermissionsResult: Listen location updates via network");
                break;
        }
    }
}
