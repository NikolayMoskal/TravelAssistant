package by.neon.travelassistant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Spinner;

import by.neon.travelassistant.R;
import by.neon.travelassistant.constant.CommonConstants;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureDrawerLayout(toolbar);
        configureNavigationView();

        loadSettingsFromPreferences();
        findViewById(R.id.lang_select).setEnabled(false);
    }

    private void configureDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                startActivity(new Intent(this, PackActivity.class));
                break;
            case R.id.nav_informer:
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_manage:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void finish() {
        saveSettingsInPreferences();
        super.finish();
    }

    private void saveSettingsInPreferences() {
        SharedPreferences preferences = getSharedPreferences(CommonConstants.APP_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String[] items = getResources().getStringArray(R.array.server_temperature_units);
        String[] signs = getResources().getStringArray(R.array.temperature_units_sign);
        Spinner unit = findViewById(R.id.temp_unit);
        int position = unit.getSelectedItemPosition();
        editor.putString(CommonConstants.TEMPERATURE_UNIT, items[position == AdapterView.INVALID_POSITION ? 0 : position]);
        editor.putString(CommonConstants.TEMPERATURE_UNIT_SIGN, signs[position == AdapterView.INVALID_POSITION ? 0 : position]);
        editor.putBoolean(CommonConstants.DISABLE_WARN, ((SwitchCompat) findViewById(R.id.switch_disable_warnings)).isChecked());
        editor.putBoolean(CommonConstants.DISABLE_ERR, ((SwitchCompat) findViewById(R.id.switch_disable_errors)).isChecked());
        editor.apply();
    }

    private void loadSettingsFromPreferences() {
        SharedPreferences preferences = getSharedPreferences(CommonConstants.APP_SETTINGS, MODE_PRIVATE);
        ((Spinner) findViewById(R.id.temp_unit)).setSelection(getUnitIndex(preferences.getString(CommonConstants.TEMPERATURE_UNIT, "Standard")));
        ((SwitchCompat) findViewById(R.id.switch_disable_warnings)).setChecked(preferences.getBoolean(CommonConstants.DISABLE_WARN, false));
        ((SwitchCompat) findViewById(R.id.switch_disable_errors)).setChecked(preferences.getBoolean(CommonConstants.DISABLE_ERR, false));
    }

    private int getUnitIndex(String unitName) {
        String[] items = getResources().getStringArray(R.array.server_temperature_units);
        for (int index = 0; index < items.length; index++) {
            if (items[index].equals(unitName)) {
                return index;
            }
        }

        return 0;
    }
}
