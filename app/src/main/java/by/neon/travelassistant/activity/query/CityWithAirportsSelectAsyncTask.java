package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CityDb;

public final class CityWithAirportsSelectAsyncTask extends AsyncTask<Void, Void, List<CityDb>> {
    private static final String TAG = "CityWithAirportsSelect";
    private boolean isSelectAll;
    private String requestedCityName;
    private long requestedCityId;

    public CityWithAirportsSelectAsyncTask(String requestedCityName) {
        this.requestedCityName = requestedCityName;
    }

    public CityWithAirportsSelectAsyncTask(long requestedCityId) {
        this.requestedCityId = requestedCityId;
    }

    public CityWithAirportsSelectAsyncTask() {
        this.isSelectAll = true;
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
    protected List<CityDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<CityDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result = dbContext.cityDao().getAllCitiesWithAirports();
        }
        else if (requestedCityName != null) {
            result = dbContext.cityDao().getCitiesWithAirportsByName(requestedCityName);
        }
        else if (requestedCityId > 0) {
            result = new ArrayList<>(0);
            result.add(dbContext.cityDao().getCityWithAirportsById(requestedCityId));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }
}
