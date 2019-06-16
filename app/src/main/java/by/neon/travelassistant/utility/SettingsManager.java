package by.neon.travelassistant.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Settings;

/**
 * Writes and reads the settings for each list of recommendations in preferences.
 */
public class SettingsManager {
    /**
     * Reads the settings for each list of recommendations from preferences.
     *
     * @param context the app context.
     * @return the list of settings
     * @throws IOException when an error occurred while reading the JSON.
     */
    public static List<Settings> readSettings(Context context) throws IOException {
        SharedPreferences preferences = context.getSharedPreferences(CommonConstants.APP_LISTS, Context.MODE_PRIVATE);
        List<Settings> settings = new ArrayList<>(0);
        ObjectMapper mapper = new ObjectMapper();
        Set<String> stringSet = preferences.getStringSet(CommonConstants.APP_SETTINGS_KEYS, new HashSet<>());
        for (String key : Objects.requireNonNull(stringSet)) {
            String json = preferences.getString(key, "");
            settings.add(mapper.readValue(json, Settings.class));
        }
        return settings;
    }

    /**
     * Writes the settings of all lists of recommendations in preferences.
     *
     * @param context      the app context.
     * @param settingsList the settings of the lists of recommendations.
     * @throws JsonProcessingException while an error occurred while writing the JSON.
     */
    public static void writeSettings(Context context, List<Settings> settingsList) throws JsonProcessingException {
        SharedPreferences preferences = context.getSharedPreferences(CommonConstants.APP_LISTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        ObjectMapper mapper = new ObjectMapper();
        Set<String> stringSet = new HashSet<>(0);
        for (Settings settings : settingsList) {
            stringSet.add(String.valueOf(settings.getCity().getCityCode()));
            editor.putString(String.valueOf(settings.getCity().getCityCode()), mapper.writeValueAsString(settings));
        }
        editor.putStringSet(CommonConstants.APP_SETTINGS_KEYS, stringSet);
        editor.apply();
    }
}
