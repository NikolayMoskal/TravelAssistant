package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

/**
 * Updates one country in database. The country replacement must be sent as param of {@code execute()} method. Other entities in {@code execute()} will be ignored.
 */
public final class CountryUpdateAsyncTask extends AsyncTask<Country, Void, Integer> {
    private static final String TAG = "CountryUpdateAsyncTask";
    private int updateResult;

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
    protected final Integer doInBackground(Country... countries) throws IllegalArgumentException {
        if (countries.length == 0) {
            throw new IllegalArgumentException("No present country to update");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        Country replacement = countries[0];
        int result = dbContext.countryDao().updateById(replacement.getId(), replacement.getCountryName(), replacement.getCountryCode());
        Log.i(TAG, "doInBackground: " + result + " rows updated.");
        return result;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     *
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Integer result) {
        updateResult = result;
    }

    /**
     * Gets the result of update entities
     *
     * @return the count of updated entities
     */
    public int getUpdateResult() {
        return updateResult;
    }
}
