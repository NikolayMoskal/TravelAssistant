package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Country;

public final class CountrySelectAsyncTask extends AsyncTask<Void, Void, List<Country>> {
    private static final String TAG = "CountrySelectAsyncTask";
    private final QuerySet querySet;

    public CountrySelectAsyncTask(QuerySet querySet) {
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
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<Country> doInBackground(Void... voids) {
        String expressions = querySet.getWhereQuery();
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Country> result = expressions == null
                ? dbContext.countryDao().getAll()
                : dbContext.countryDao().getByQuery(expressions);
        int resultSize = result == null ? 0 : result.size();
        Log.i(TAG, "doInBackground: " + resultSize + " rows returned.");
        return result == null ? new ArrayList<>(0) : result;
    }
}
