package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.AirportDb;

/**
 * Removes airports from database. To run the request to database use the {@code execute()} method.
 */
public final class AirportDeleteAsyncTask extends AsyncTask<AirportDb, Void, Integer> {
    private static final String TAG = "AirportDeleteAsyncTask";
    private boolean isDeleteAll;
    private String requestedAirportName;
    private long requestedCityId;
    private Long requestedAirportId;

    /**
     * Builds a new instance of {@link AirportDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one airport from database. The target airport must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all airports from database.
     */
    public AirportDeleteAsyncTask() {
        this.isDeleteAll = true;
    }

    /**
     * Builds a new instance of {@link AirportDeleteAsyncTask} using the query set.
     * Removes one or more airports from database. All airports to remove must be sent as params of {@code execute()} method.
     *
     * @param airportName the set of expressions to database
     */
    public AirportDeleteAsyncTask(String airportName) {
        this.requestedAirportName = airportName;
    }

    public AirportDeleteAsyncTask(long requestedCityId) {
        this.requestedCityId = requestedCityId;
    }

    public AirportDeleteAsyncTask(Long requestedAirportId) {
        this.requestedAirportId = requestedAirportId;
    }

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
    protected Integer doInBackground(AirportDb... airportDbs) throws IllegalArgumentException, NullPointerException {
        if (airportDbs.length == 0) {
            throw new IllegalArgumentException("No present airport to delete.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = 0;
        if (isDeleteAll) {
            result = dbContext.airportDao().deleteAll();
        }
        else if (requestedAirportName != null) {
            result = dbContext.airportDao().deleteByName(requestedAirportName);
        }
        else if (requestedCityId > 0) {
            result = dbContext.airportDao().deleteByCity(requestedCityId);
        }
        else if (requestedAirportId > 0) {
            result = dbContext.airportDao().deleteById(requestedAirportId);
        }
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
