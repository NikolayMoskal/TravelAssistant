package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Airport;

/**
 * Removes airports from database. To run the request to database use the {@code execute()} method.
 */
public final class AirportDeleteAsyncTask extends AsyncTask<Airport, Void, Integer> {
    private static final String TAG = "AirportDeleteAsyncTask";
    private boolean isDeleteAll;
    private final QuerySet querySet;

    /**
     * Builds a new instance of {@link AirportDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one airport from database. The target airport must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all airports from database.
     *
     * @param isDeleteAll the flag to remove all airports
     */
    public AirportDeleteAsyncTask(boolean isDeleteAll) {
        this.querySet = new QuerySet(null);
        this.isDeleteAll = isDeleteAll;
    }

    /**
     * Builds a new instance of {@link AirportDeleteAsyncTask} using the query set.
     * Removes one or more airports from database. All airports to remove must be sent as params of {@code execute()} method.
     *
     * @param querySet the set of expressions to database
     */
    public AirportDeleteAsyncTask(QuerySet querySet) {
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
     * @param airports The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(Airport... airports) throws IllegalArgumentException, NullPointerException {
        if (airports.length == 0) {
            throw new IllegalArgumentException("No present airport to delete.");
        }

        String expressions = querySet.getWhereQuery();

        if (!isDeleteAll && expressions == null && airports[0] == null) {
            throw new NullPointerException("No airport to remove");
        }

        Airport target = airports[0];
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = expressions == null
                ? dbContext.airportDao().deleteById(target.getId())
                : isDeleteAll
                ? dbContext.airportDao().deleteAll()
                : dbContext.airportDao().deleteByQuery(expressions);
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
