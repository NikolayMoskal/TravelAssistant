package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

public final class ThingSelectAsyncTask extends AsyncTask<Void, Void, List<ThingDb>> {
    private static final String TAG = "ThingSelectAsyncTask";
    private boolean isSelectAll;
    private String requestedName;
    private long requestedId;
    private String requestedType;
    private List<String> requestedCategories;

    public ThingSelectAsyncTask() {
        this.isSelectAll = true;
    }

    public ThingSelectAsyncTask(String requestedName) {
        this.requestedName = requestedName;
    }

    public ThingSelectAsyncTask(long requestedId) {
        this.requestedId = requestedId;
    }

    public ThingSelectAsyncTask(String requestedType, List<String> requestedCategories) {
        this.requestedType = requestedType;
        this.requestedCategories = requestedCategories;
    }

    public ThingSelectAsyncTask(List<String> requestedCategories) {
        this.requestedCategories = requestedCategories;
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
    protected List<ThingDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<ThingDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result = dbContext.thingDao().getAll();
        }
        else if (requestedId > 0) {
            result.add(dbContext.thingDao().getById(requestedId));
        }
        else if (requestedName != null) {
            result = dbContext.thingDao().getByName(requestedName);
        }
        else if (requestedType != null) {
            result = requestedCategories != null
                    ? dbContext.thingDao().getByCategoriesAndType(requestedCategories, requestedType)
                    : dbContext.thingDao().getByType(requestedType);
        }
        else if (requestedCategories != null) {
            result = dbContext.thingDao().getByCategories(requestedCategories);
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }
}
