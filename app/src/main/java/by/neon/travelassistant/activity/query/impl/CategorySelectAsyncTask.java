package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.SelectAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;

/**
 * Provides a functionality for selection the thing categories from database using Room. All parameters
 * used in following order:
 * <ol>
 * <li>All records.</li>
 * <li>Unique ID.</li>
 * <li>List of categories.</li>
 * </ol>
 */
public final class CategorySelectAsyncTask extends SelectAsyncTask<CategoryDb> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "CategorySelectAsyncTask";
    /**
     * The collection of selectable category names.
     */
    private List<String> names;

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
    protected List<CategoryDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<CategoryDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result.addAll(dbContext.getCategoryDao().getAll());
        } else if (id > 0) {
            result.add(dbContext.getCategoryDao().getById(id));
        } else if (names != null && names.size() > 0) {
            if (names.size() > 1) {
                result.addAll(dbContext.getCategoryDao().getByNames(names));
            } else {
                result.add(dbContext.getCategoryDao().getByName(names.get(0)));
            }
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    /**
     * Sets the list of selectable category names.
     *
     * @param names the names to set.
     */
    public void setNames(List<String> names) {
        this.names = names;
    }
}
