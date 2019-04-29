package by.neon.travelassistant.config;

import android.app.Activity;

public final class OwmConfig extends Config {
    private static final String TAG = "OwmConfig";
    private Activity activity;

    public OwmConfig(Activity activity) {
        this.activity = activity;
    }
}
