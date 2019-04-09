package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Airport;

/**
 * Returns a list of airports by given query set. It may contains a set of columns and/or set of filters.
 */
public final class AirportSelectAsyncTask extends AsyncTask<Void, Void, List<Airport>> {
    private static final String TAG = "AirportSelectAsyncTask";
    private final QuerySet querySet;

    /**
     * Builds a new {@link AirportSelectAsyncTask} with query set
     *
     * @param querySet the set of columns to SQL SELECT clause and/or the set of expressions to SQL WHERE clause
     */
    public AirportSelectAsyncTask(QuerySet querySet) {
        this.querySet = querySet;
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
    protected List<Airport> doInBackground(Void... voids) {
        String expressions = querySet.getWhereQuery();
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Airport> result = expressions == null
                ? dbContext.airportDao().getAll()
                : dbContext.airportDao().getByQuery(expressions);
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }
}
