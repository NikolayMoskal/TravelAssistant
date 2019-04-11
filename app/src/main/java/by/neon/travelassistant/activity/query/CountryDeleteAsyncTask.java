package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

/**
 * Removes cities from database. To run the request to database use the {@code execute()} method.
 */
public final class CountryDeleteAsyncTask extends AsyncTask<Country, Void, Integer> {
    private static final String TAG = "CountryDeleteAsyncTask";
    private boolean isDeleteAll;
    private final QuerySet querySet;

    /**
     * Builds a new instance of {@link CountryDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one country from database. The target country must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all countries from database.
     *
     * @param isDeleteAll the flag to remove all countries
     */
    public CountryDeleteAsyncTask(boolean isDeleteAll) {
        this.isDeleteAll = isDeleteAll;
        this.querySet = new QuerySet(null);
    }

    /**
     * Builds a new instance of {@link CityDeleteAsyncTask} using the query set.
     * Removes one or more countries from database. All countries to remove must be sent as params of {@code execute()} method.
     *
     * @param querySet the set of expressions to database
     */
    public CountryDeleteAsyncTask(QuerySet querySet) {
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
     * @param countries The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(Country... countries) throws IllegalArgumentException {
        if (countries.length == 0) {
            throw new IllegalArgumentException("No present country to delete.");
        }

        String expressions = querySet.getWhereQuery();

        if (!isDeleteAll && expressions == null && countries[0] == null) {
            throw new NullPointerException("No airport to remove");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        Country target = countries[0];
        int result = expressions == null
                ? dbContext.countryDao().deleteById(target.getId())
                : isDeleteAll
                ? dbContext.countryDao().deleteAll()
                : dbContext.countryDao().deleteByQuery(expressions);
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
