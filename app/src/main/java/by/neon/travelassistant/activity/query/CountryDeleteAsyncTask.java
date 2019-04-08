package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

/**
 * Removes one country from database. The target country must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
 */
public final class CountryDeleteAsyncTask extends AsyncTask<Country, Void, Integer> {
    private static final String TAG = "CountryDeleteAsyncTask";

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

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = dbContext.countryDao().delete(countries[0]);
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
