package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CountryDb;

public final class CountryWithCitiesInsertAsyncTask extends AsyncTask<CountryDb, Void, List<Long>> {
    private static final String TAG = "CountryWithCitiesInsert";

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
    protected List<Long> doInBackground(CountryDb... countries) {
        if (countries.length == 0) {
            throw new IllegalArgumentException("No present countries with related cities to insert. Must be at least 1 record.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Long> result = new ArrayList<>(0);
        for (CountryDb countryDb : countries) {
            result.add(dbContext.countryDao().insertCountryWithCities(countryDb));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " records inserted.");
        return result;
    }
}
