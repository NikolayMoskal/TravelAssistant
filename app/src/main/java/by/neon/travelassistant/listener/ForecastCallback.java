package by.neon.travelassistant.listener;

import java.util.List;

import by.neon.travelassistant.model.Weather;

/**
 * The bridge interface between the activity and the listener. Used to output the response from
 * asynchronous task thread to synchronous activity thread.
 */
public interface ForecastCallback {
    /**
     * Use this method to output the response from asynchronous threads.
     *
     * @param weatherList the captured response with the list of weather snapshots.
     */
    void onSuccess(List<Weather> weatherList);
}
