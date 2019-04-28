package by.neon.travelassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.OwmConfig;
import by.neon.travelassistant.listener.AutoCompleteTextViewItemClickListener;
import by.neon.travelassistant.model.OwmCity;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            config = new OwmConfig(this);
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }

        configureArrivalAirportView();
        configureDepartureAirportView();
    }

    /**
     * Configures the view for arrival airport
     */
    private void configureArrivalAirportView() {
        AutoCompleteTextView textView = findViewById(R.id.arv_city);
        textView.setAdapter(configureAdapter());
        textView.setOnItemClickListener(new AutoCompleteTextViewItemClickListener(textView));
    }

    /**
     * Configures the view for departure airport
     */
    private void configureDepartureAirportView() {
        AutoCompleteTextView textView = findViewById(R.id.dep_city);
        textView.setAdapter(configureAdapter());
        textView.setOnItemClickListener(new AutoCompleteTextViewItemClickListener(textView));
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

    private SimpleAdapter configureAdapter() {
        String[] keys = new String[]{"CityName", "CityId"};
        ArrayList<HashMap<String, String>> maps = new ArrayList<>(0);
        for (OwmCity info : config.getCities()) {
            HashMap<String, String> map = new HashMap<>(0);
            map.put(keys[0], String.format("%s, %s", info.getName(), info.getCountryCode()));
            map.put(keys[1], String.valueOf(info.getOwmId()));
            maps.add(map);
        }
        int[] ids = new int[]{R.id.cityInfo, R.id.cityId};
        return new SimpleAdapter(this, maps, R.layout.list_item, keys, ids);
    }
}
