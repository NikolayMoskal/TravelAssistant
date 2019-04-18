package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.AirportDb;

/**
 * Returns a list of airports by given query set. It may contains a set of columns and/or set of filters.
 */
public final class AirportSelectAsyncTask extends AsyncTask<Void, Void, List<AirportDb>> {
    private static final String TAG = "AirportSelectAsyncTask";
    private boolean isSelectAll;
    private String requestedAirportName;
    private long requestedAirportId;
    private Long requestedCityId;

    /**
     * Builds a new {@link AirportSelectAsyncTask} with query set
     *
     * @param requestedAirportName the set of columns to SQL SELECT clause and/or the set of expressions to SQL WHERE clause
     */
    public AirportSelectAsyncTask(String requestedAirportName) {
        this.requestedAirportName = requestedAirportName;
    }

    public AirportSelectAsyncTask() {
        this.isSelectAll = true;
    }

    public AirportSelectAsyncTask(long requestedAirportId) {
        this.requestedAirportId = requestedAirportId;
    }

    public AirportSelectAsyncTask(Long requestedCityId) {
        this.requestedCityId = requestedCityId;
    }

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
    protected List<AirportDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<AirportDb> result = new ArrayList<>();
        if (isSelectAll) {
            result = dbContext.airportDao().getAll();
        }
        else if (requestedAirportName != null) {
            result = dbContext.airportDao().getByName(requestedAirportName);
        }
        else if (requestedAirportId > 0) {
            result = new ArrayList<>();
            result.add(dbContext.airportDao().getById(requestedAirportId));
        }
        else if (requestedCityId > 0) {
            result = dbContext.airportDao().getByCity(requestedCityId);
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }
}
