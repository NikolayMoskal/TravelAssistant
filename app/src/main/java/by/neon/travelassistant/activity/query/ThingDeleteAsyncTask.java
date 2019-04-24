package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

public final class ThingDeleteAsyncTask extends AsyncTask<ThingDb, Void, Integer> {
    private static final String TAG = "ThingDeleteAsyncTask";
    private boolean isDeleteAll;
    private String requestedName;
    private long requestedId;

    public ThingDeleteAsyncTask() {
        this.isDeleteAll = true;
    }

    public ThingDeleteAsyncTask(String requestedName) {
        this.requestedName = requestedName;
    }

    public ThingDeleteAsyncTask(long requestedId) {
        this.requestedId = requestedId;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param thingDbs The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(ThingDb... thingDbs) {
        if (thingDbs.length == 0) {
            throw new IllegalArgumentException("No present thing to delete.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = 0;
        if (isDeleteAll) {
            result = dbContext.thingDao().deleteAll();
        }
        else if (requestedId > 0) {
            result = dbContext.thingDao().deleteById(requestedId);
        }
        else if (requestedName != null) {
            result = dbContext.thingDao().deleteByName(requestedName);
        }
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }
}
