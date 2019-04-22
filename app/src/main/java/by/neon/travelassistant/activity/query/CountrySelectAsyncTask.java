package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CountryDb;

public final class CountrySelectAsyncTask extends AsyncTask<Void, Void, List<CountryDb>> {
    private static final String TAG = "CountrySelectAsyncTask";
    private boolean isSelectAll;
    private String requestedCountryName;
    private long requestedCountryId;

    public CountrySelectAsyncTask() {
        this.isSelectAll = true;
    }

    public CountrySelectAsyncTask(String requestedCountryName) {
        this.requestedCountryName = requestedCountryName;
    }

    public CountrySelectAsyncTask(long requestedCountryId) {
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
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<CountryDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<CountryDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result = dbContext.countryDao().getAll();
        }
        else if (requestedCountryName != null) {
            result = dbContext.countryDao().getByName(requestedCountryName);
        }
        else if (requestedCountryId > 0) {
            result.add(dbContext.countryDao().getById(requestedCountryId));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }
}