package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.AirportDb;

/**
 * Inserts one or more airports into database.
 */
public final class AirportInsertAsyncTask extends AsyncTask<AirportDb, Void, List<Long>> {
    private static final String TAG = "AirportInsertAsyncTask";

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param airportDbs The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<Long> doInBackground(AirportDb... airportDbs) throws IllegalArgumentException {
        if (airportDbs.length == 0) {
            throw new IllegalArgumentException("No present airportDbs to insert. Must be at least 1 airport.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Long> result = dbContext.airportDao().insert(airportDbs);
        int resultSize = result == null ? 0 : result.size();
        Log.i(TAG, "doInBackground: " + resultSize + " rows inserted.");
        return result == null ? new ArrayList<>(0) : result;
    }
}
