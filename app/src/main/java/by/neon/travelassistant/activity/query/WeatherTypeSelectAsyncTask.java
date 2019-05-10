package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

public final class WeatherTypeSelectAsyncTask extends AsyncTask<Void, Void, List<WeatherTypeDb>> {
    private static final String TAG = "WeatherTypeSelect";
    private boolean isSelectAll;
    private long id;
    private String name;

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
    protected List<WeatherTypeDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<WeatherTypeDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result.addAll(dbContext.getWeatherTypeDao().getAll());
        } else if (id > 0) {
            result.add(dbContext.getWeatherTypeDao().getById(id));
        } else if (name != null) {
            result.add(dbContext.getWeatherTypeDao().getByName(name));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
