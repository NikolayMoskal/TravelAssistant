package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.City;

/**
 * Removes cities from database. To run the request to database use the {@code execute()} method.
 */
public final class CityDeleteAsyncTask extends AsyncTask<City, Void, Integer> {
    private static final String TAG = "CityDeleteAsyncTask";
    private boolean isDeleteAll;
    private final QuerySet querySet;

    /**
     * Builds a new instance of {@link CityDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one city from database. The target city must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all cities from database.
     *
     * @param isDeleteAll the flag to remove all cities
     */
    public CityDeleteAsyncTask(boolean isDeleteAll) {
        this.querySet = new QuerySet(null);
        this.isDeleteAll = isDeleteAll;
    }

    /**
     * Builds a new instance of {@link CityDeleteAsyncTask} using the query set.
     * Removes one or more cities from database. All cities to remove must be sent as params of {@code execute()} method.
     *
     * @param querySet the set of expressions to database
     */
    public CityDeleteAsyncTask(QuerySet querySet) {
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
     * @param cities The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(City... cities) throws IllegalArgumentException {
        if (cities.length == 0) {
            throw new IllegalArgumentException("No present city to delete.");
        }

        String expressions = querySet.getWhereQuery();

        if (!isDeleteAll && expressions == null && cities[0] == null) {
            throw new NullPointerException("No airport to remove");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        City target = cities[0];
        int result = expressions == null
                ? dbContext.cityDao().deleteById(target.getId())
                : isDeleteAll
                ? dbContext.cityDao().deleteAll()
                : dbContext.cityDao().deleteByQuery(expressions);
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
