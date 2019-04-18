package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CountryDb;

/**
 * Removes cities from database. To run the request to database use the {@code execute()} method.
 */
public final class CountryDeleteAsyncTask extends AsyncTask<CountryDb, Void, Integer> {
    private static final String TAG = "CountryDeleteAsyncTask";
    private boolean isDeleteAll;
    private String requestedCountryName;
    private long requestedCountryId;

    /**
     * Builds a new instance of {@link CountryDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one country from database. The target country must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all countries from database.
     *
     * @param isDeleteAll the flag to remove all countries
     */
    public CountryDeleteAsyncTask() {
        this.isDeleteAll = true;
    }

    public CountryDeleteAsyncTask(String requestedCountryName) {
        this.requestedCountryName = requestedCountryName;
    }

    public CountryDeleteAsyncTask(long requestedCountryId) {
        this.requestedCountryId = requestedCountryId;
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
    protected Integer doInBackground(CountryDb... countries) throws IllegalArgumentException {
        if (countries.length == 0) {
            throw new IllegalArgumentException("No present country to delete.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = 0;
        if (isDeleteAll) {
            result = dbContext.countryDao().deleteAll();
        }
        else if (requestedCountryName != null) {
            result = dbContext.countryDao().deleteByName(requestedCountryName);
        }
        else if (requestedCountryId > 0) {
            result = dbContext.countryDao().deleteById(requestedCountryId);
        }
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
