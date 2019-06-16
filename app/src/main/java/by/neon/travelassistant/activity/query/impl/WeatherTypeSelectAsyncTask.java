package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.SelectAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

/**
 * Provides a functionality for selection the weather types from database using Room. All parameters
 * used in following order:
 * <ol>
 * <li>All records.</li>
 * <li>Unique ID.</li>
 * <li>The name of a weather type.</li>
 * </ol>
 */
public final class WeatherTypeSelectAsyncTask extends SelectAsyncTask<WeatherTypeDb> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "WeatherTypeSelect";
    /**
     * The name of selectable weather type.
     */
    private String name;

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<WeatherTypeDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<WeatherTypeDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result.addAll(dbContext.getWeatherTypeDao().getAll());
        } else if (id > 0) {
            result.add(dbContext.getWeatherTypeDao().getById(id));
        } else if (name != null) {
            result.add(dbContext.getWeatherTypeDao().getByName(name));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    /**
     * Sets the name of selectable weather type.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
