package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

/**
 * Inserts one or more countries into database.
 */
public final class CountryInsertAsyncTask extends AsyncTask<Country, Void, List<Long>> {
    private static final String TAG = "CountryInsertAsyncTask";
    private List<Long> insertResult;

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
    protected List<Long> doInBackground(Country... countries) throws IllegalArgumentException {
        if (countries.length == 0) {
            throw new IllegalArgumentException("No present countries to insert. Must be at least 1 country.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Long> result = dbContext.countryDao().insert(countries);
        Log.i(TAG, "doInBackground: " + result.size() + " rows inserted.");
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
    protected void onPostExecute(List<Long> result) {
        insertResult = result;
    }

    /**
     * Gets the result of insert entities
     *
     * @return the count of inserted entities
     */
    public List<Long> getInsertResult() {
        return new ArrayList<>(insertResult);
    }
}
