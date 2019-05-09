package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

public final class ThingUpdateAsyncTask extends AsyncTask<ThingDb, Void, Integer> {
    private static final String TAG = "ThingUpdateAsyncTask";

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
        if (thingDbs == null || thingDbs.length == 0 || thingDbs[0] == null) {
            throw new IllegalArgumentException("No thing to update.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        int result = dbContext.getThingDao().update(thingDbs[0]);
        Log.i(TAG, "doInBackground: " + result + " rows updated.");
        return result;
    }
}
