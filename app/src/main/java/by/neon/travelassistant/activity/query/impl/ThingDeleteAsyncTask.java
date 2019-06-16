package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.DeleteAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

/**
 * Provides a functionality for delete the things from database using Room. All parameters
 * used in following order:
 * <ol>
 * <li>All records.</li>
 * <li>Unique ID.</li>
 * <li>Thing name in english.</li>
 * </ol>
 */
public final class ThingDeleteAsyncTask extends DeleteAsyncTask<ThingDb> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "ThingDeleteAsyncTask";
    /**
     * The name of deletable thing.
     */
    private String name;

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
            result = dbContext.getThingDao().deleteAll();
        } else if (id > 0) {
            result = dbContext.getThingDao().deleteById(id);
        } else if (name != null) {
            result = dbContext.getThingDao().deleteByName(name);
        }
        Log.i(TAG, "doInBackground: " + result + " rows deleted.");
        return result;
    }

    /**
     * Sets the name of deletable thing.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
