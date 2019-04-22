package by.neon.travelassistant.activity;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import by.neon.travelassistant.R;
import by.neon.travelassistant.config.Config;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.config.ThingModel;
import by.neon.travelassistant.listener.SwitchCheckedListener;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureDrawerLayout(toolbar);
        configureNavigationView();

        config = new SqliteConfig();
        createSwitchForEachThing();
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

    @SuppressWarnings("StatementWithEmptyBody")
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

    private void createSwitchForEachThing() {
        LinearLayout layout = findViewById(R.id.layout_switch);
        for (ThingModel thing : config.getThings()) {
            SwitchCompat compat = new SwitchCompat(this);
            compat.setText(thing.getThingName());
            compat.setChecked(true);
            compat.setTextSize(getResources().getDimension(R.dimen.settings_activity_text_size) / getResources().getDisplayMetrics().density);
            compat.setTextColor(Color.rgb(0x00, 0x00, 0x00));
            compat.setOnCheckedChangeListener(new SwitchCheckedListener());
            compat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(compat);
        }
    }
}
