package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Airport;

/**
 * Updates one airport in database. The airport replacement should be sent as param to {@code execute()} method. Other entities in {@code execute()} method will be ignored.
 */
public final class AirportUpdateAsyncTask extends AsyncTask<Airport, Void, Integer> {
    private static final String TAG = "AirportUpdateAsyncTask";

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param airports The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(Airport... airports) throws IllegalArgumentException {
        if (airports.length == 0) {
            throw new IllegalArgumentException("No present airport to update.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = dbContext.airportDao().update(airports[0]);
        Log.i(TAG, "doInBackground: " + result + " rows updated.");
        return result;
    }
}
