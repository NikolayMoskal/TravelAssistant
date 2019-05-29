package by.neon.travelassistant.listener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CompoundButton;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import by.neon.travelassistant.R;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.model.Transport;

public class ThingSelectListener implements CompoundButton.OnCheckedChangeListener {
    private WeakReference<Activity> activity;
    private Transport transport;
    private Snackbar snackbar;
    private boolean isFrozen;
    private SharedPreferences settings;
    private Timer timer;
    private double allWeight;

    public ThingSelectListener(Activity activity, Transport transport) {
        this.activity = new WeakReference<>(activity);
        this.transport = transport;
        this.settings = activity.getSharedPreferences(CommonConstants.APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public double getAllWeight() {
        return allWeight;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            allWeight += ((Thing) buttonView.getTag()).getWeight();
        } else {
            allWeight -= ((Thing) buttonView.getTag()).getWeight();
        }

        if (allWeight > transport.getMaxWeight()) {
            snackbar = setSnackBar(String.format(Locale.getDefault(),
                    "%s (%.2f%s) %s %.2f%s",
                    activity.get().getResources().getString(R.string.weight_warning),
                    transport.getHandPackWeight(),
                    activity.get().getResources().getString(R.string.mass_unit_abbr),
                    activity.get().getResources().getString(R.string.current_weight_message),
                    allWeight,
                    activity.get().getResources().getString(R.string.mass_unit_abbr)));
            if (!snackbar.isShown() && !isFrozen && !settings.getBoolean(CommonConstants.DISABLE_WARN, false)) {
                View view = snackbar.getView();
                view.setBackgroundColor(Color.HSVToColor(new float[]{0.0f, 100.0f, 100.0f}));
                snackbar.show();
            }
        } else if (allWeight > transport.getHandPackWeight()) {
            snackbar = setSnackBar(String.format(Locale.getDefault(),
                    "%s (%.2f%s) %s %.2f%s",
                    activity.get().getResources().getString(R.string.weight_warning),
                    transport.getHandPackWeight(),
                    activity.get().getResources().getString(R.string.mass_unit_abbr),
                    activity.get().getResources().getString(R.string.current_weight_message),
                    allWeight,
                    activity.get().getResources().getString(R.string.mass_unit_abbr)));
            if (!snackbar.isShown() && !isFrozen && !settings.getBoolean(CommonConstants.DISABLE_ERR, false)) {
                View view = snackbar.getView();
                view.setBackgroundColor(Color.HSVToColor(new float[]{30.0f, 100.0f, 100.0f}));
                snackbar.show();
            }
        } else {
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    }

    private Snackbar setSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(activity.get().findViewById(R.id.layout_preview_root),
                        message,
                        Snackbar.LENGTH_LONG)
                .setAction(R.string.action_ok, this::onActionClick);
        snackbar.setDuration(5000);
        return snackbar;
    }

    private void onActionClick(@SuppressWarnings("unused") View view) {
        if (timer != null) {
            timer.cancel();
        }
        isFrozen = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!settings.getBoolean(CommonConstants.DISABLE_WARN, false) ||
                        !settings.getBoolean(CommonConstants.DISABLE_ERR, false)) {
                    isFrozen = false;
                    snackbar.show();
                }
            }
        }, 30 * 60 * 1000);
    }
}
