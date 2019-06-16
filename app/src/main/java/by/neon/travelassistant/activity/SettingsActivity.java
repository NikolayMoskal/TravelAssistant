package by.neon.travelassistant.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Spinner;

import by.neon.travelassistant.R;
import by.neon.travelassistant.constant.CommonConstants;

/**
 * Represents the activity to show the settings of this application.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadSettingsFromPreferences();
        findViewById(R.id.lang_select).setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        saveSettingsInPreferences();
        super.onBackPressed();
    }

    /**
     * Unloads the settings to preferences.
     */
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

    /**
     * Loads the settings from preferences.
     */
    private void loadSettingsFromPreferences() {
        SharedPreferences preferences = getSharedPreferences(CommonConstants.APP_SETTINGS, MODE_PRIVATE);
        ((Spinner) findViewById(R.id.temp_unit)).setSelection(getUnitIndex(preferences.getString(CommonConstants.TEMPERATURE_UNIT, "Standard")));
        ((SwitchCompat) findViewById(R.id.switch_disable_warnings)).setChecked(preferences.getBoolean(CommonConstants.DISABLE_WARN, false));
        ((SwitchCompat) findViewById(R.id.switch_disable_errors)).setChecked(preferences.getBoolean(CommonConstants.DISABLE_ERR, false));
    }

    /**
     * Gets the index of given temperature unit in the list.
     *
     * @param unitName the name of temperature unit.
     * @return the index in the list or 0 if this temperature unit is not found.
     */
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
