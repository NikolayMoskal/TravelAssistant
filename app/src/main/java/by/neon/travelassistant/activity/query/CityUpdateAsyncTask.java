package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CityDb;

/**
 * Updates one city in database. The city replacement must be sent as param of {@code execute()} method. Other entities in {@code execute()} will be ignored.
 */
public final class CityUpdateAsyncTask extends AsyncTask<CityDb, Void, Integer> {
    private static final String TAG = "CityUpdateAsyncTask";

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param cities The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(CityDb... cities) throws IllegalArgumentException, NullPointerException {
        if (cities.length == 0) {
            throw new IllegalArgumentException("No present city to update.");
        }
        if (cities[0] == null) {
            throw new NullPointerException("No city to update.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        CityDb replacement = cities[0];
        int result = dbContext.cityDao().updateById(replacement.getId(), replacement.getCityCode(), replacement.getCityName());
        Log.i(TAG, "doInBackground: " + result + " rows updated.");
        return result;
    }
}
