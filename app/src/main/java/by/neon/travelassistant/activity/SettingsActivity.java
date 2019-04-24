package by.neon.travelassistant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.listener.SwitchCheckedListener;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "SettingsActivity";
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureDrawerLayout(toolbar);
        configureNavigationView();

        try {
            config = new SqliteConfig();
            createSwitchForEachThing();
            loadSettingsFromPreferences();
        }
        catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }
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
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
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

    @Override
    public void finish() {
        saveSettingsInPreferences();
        super.finish();
    }

    private void createSwitchForEachThing() {
        LinearLayout layout = findViewById(R.id.layout_switch);
        Locale locale = Locale.getDefault();

        for (Thing thing : config.getThings()) {
            SwitchCompat compat = new SwitchCompat(this);
            compat.setText(locale.getLanguage().equals("ru") ? thing.getThingNameRu() : thing.getThingNameEn());
            compat.setChecked(false);
            compat.setTextSize(getResources().getDimension(R.dimen.settings_activity_text_size) / getResources().getDisplayMetrics().density);
            compat.setTextColor(Color.rgb(0x00, 0x00, 0x00));
            compat.setOnCheckedChangeListener(new SwitchCheckedListener());
            compat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(compat);
        }
    }

    private void saveSettingsInPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        LinearLayout layout = findViewById(R.id.layout_switch);
        for (int index = 0; index < layout.getChildCount(); index++) {
            View view = layout.getChildAt(index);
            if (!(view instanceof SwitchCompat)) {
                continue;
            }

            SwitchCompat compat = (SwitchCompat) view;
            editor.putBoolean(compat.getText().toString(), compat.isChecked());
        }

        editor.apply();
    }

    private void loadSettingsFromPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        LinearLayout layout = findViewById(R.id.layout_switch);
        for (int index = 0; index < layout.getChildCount(); index++) {
            View view = layout.getChildAt(index);
            if (!(view instanceof SwitchCompat)) {
                continue;
            }

            SwitchCompat compat = (SwitchCompat) view;
            compat.setChecked(preferences.getBoolean(compat.getText().toString(), false));
        }
    }
}
