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

/**
 * Handles the thing selection in the list of recommendations. Calculates the weight of selected things
 * and raises the messages when this weight is greater than threshold values.
 */
public class ThingSelectListener implements CompoundButton.OnCheckedChangeListener {
    /**
     * The reference to activity that uses this listener.
     */
    private WeakReference<Activity> activity;
    /**
     * The transport type that determines the threshold values for the weight.
     */
    private Transport transport;
    /**
     * The {@link Snackbar} to show the messages.
     */
    private Snackbar snackbar;
    /**
     * If true then the messages is not shows anytime.
     */
    private boolean isFrozen;
    /**
     * The preferences that contains te settings to disable the messages.
     */
    private SharedPreferences settings;
    /**
     * The timer to show the messages with some period.
     */
    private Timer timer;
    /**
     * The weight of all selected things.
     */
    private double allWeight;

    /**
     * Builds a new instance of {@link ThingSelectListener} with given {@link Transport} that
     * determines the limits of weight and the activity that uses this instance.
     *
     * @param activity  the activity that uses this listener.
     * @param transport the selected transport type that determines the threshold values for the weight.
     */
    public ThingSelectListener(Activity activity, Transport transport) {
        this.activity = new WeakReference<>(activity);
        this.transport = transport;
        this.settings = activity.getSharedPreferences(CommonConstants.APP_SETTINGS, Context.MODE_PRIVATE);
    }

    /**
     * Gets the calculated weight of all things in the list.
     *
     * @return the all weight.
     */
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
                    "%s (%s). %s %s.",
                    activity.get().getResources().getString(R.string.weight_error),
                    activity.get().getResources().getString(R.string.weight_title, transport.getMaxWeight()),
                    activity.get().getResources().getString(R.string.current_weight_message),
                    activity.get().getResources().getString(R.string.weight_title, allWeight)));
            if (!snackbar.isShown() && !isFrozen && !settings.getBoolean(CommonConstants.DISABLE_WARN, false)) {
                View view = snackbar.getView();
                view.setBackgroundColor(Color.HSVToColor(new float[]{0.0f, 100.0f, 100.0f}));
                snackbar.show();
            }
        } else if (allWeight > transport.getHandPackWeight()) {
            snackbar = setSnackBar(String.format(Locale.getDefault(),
                    "%s (%s). %s %s.",
                    activity.get().getResources().getString(R.string.weight_error),
                    activity.get().getResources().getString(R.string.weight_title, transport.getHandPackWeight()),
                    activity.get().getResources().getString(R.string.current_weight_message),
                    activity.get().getResources().getString(R.string.weight_title, allWeight)));
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

    /**
     * Makes the {@link Snackbar} with given message.
     *
     * @param message the message to show.
     * @return the created snackbar.
     */
    private Snackbar setSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(activity.get().findViewById(R.id.layout_preview_root),
                        message,
                        Snackbar.LENGTH_LONG)
                .setAction(R.string.action_ok, this::onActionClick);
        snackbar.setDuration(5000);
        return snackbar;
    }

    /**
     * Closes the snackbar by click on the button.
     *
     * @param view the parent view.
     */
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
