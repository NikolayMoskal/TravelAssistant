package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.InsertAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;

/**
 * Provides a functionality for insert the thing categories into database using Room.
 */
public final class CategoryInsertAsyncTask extends InsertAsyncTask<CategoryDb> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "CategoryInsertAsyncTask";

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param categoryDbs The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<Long> doInBackground(CategoryDb... categoryDbs) {
        if (categoryDbs == null || categoryDbs.length == 0) {
            throw new IllegalArgumentException("No entities to insert");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Long> result = dbContext.getCategoryDao().insert(categoryDbs);
        int resultSize = result == null ? 0 : result.size();
        Log.i(TAG, "doInBackground: " + resultSize + " rows inserted.");
        return result;
    }
}
