package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CityDb;

/**
 * Removes cities from database. To run the request to database use the {@code execute()} method.
 */
public final class CityDeleteAsyncTask extends AsyncTask<CityDb, Void, Integer> {
    private static final String TAG = "CityDeleteAsyncTask";
    private boolean isDeleteAll;
    private String requestedCityName;
    private long requestedCountryId;
    private Long requestedCityId;

    /**
     * Builds a new instance of {@link CityDeleteAsyncTask}.
     * If {@link #isDeleteAll} is {@code true} then removes only one city from database. The target city must be sent as first element of varargs in {@code execute()} method. Other entities in {@code execute()} method will be ignored.
     * if {@link #isDeleteAll} if {@code false} then removes all cities from database.
     */
    public CityDeleteAsyncTask() {
        this.isDeleteAll = true;
    }

    public CityDeleteAsyncTask(String requestedCityName) {
        this.requestedCityName = requestedCityName;
    }

    public CityDeleteAsyncTask(long requestedCountryId) {
        this.requestedCountryId = requestedCountryId;
    }

    public CityDeleteAsyncTask(Long requestedCityId) {
        this.requestedCityId = requestedCityId;
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
    protected Integer doInBackground(CityDb... cities) throws IllegalArgumentException {
        if (cities.length == 0) {
            throw new IllegalArgumentException("No present city to delete.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = 0;
        if (isDeleteAll) {
            result = dbContext.cityDao().deleteAll();
        }
        else if (requestedCityName != null) {
            result = dbContext.cityDao().deleteByName(requestedCityName);
        }
        else if (requestedCountryId > 0) {
            result = dbContext.cityDao().deleteByCountry(requestedCountryId);
        }
        else if (requestedCityId > 0) {
            result = dbContext.cityDao().deleteById(requestedCityId);
        }
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
