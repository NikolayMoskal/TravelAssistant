package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.SelectAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

/**
 * Provides a functionality for selection the things from database using Room. All parameters
 * used in following order:
 * <ol>
 * <li>All records.</li>
 * <li>Unique ID.</li>
 * <li>Thing name in english.</li>
 * <li>Thing type (clothes, shoes etc).</li>
 * <li>Thing category (for travel, for photo etc).</li>
 * </ol>
 */
public final class ThingSelectAsyncTask extends SelectAsyncTask<ThingDb> {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "ThingSelectAsyncTask";
    /**
     * The name of selectable thing.
     */
    private String name;
    /**
     * The type of selectable thing.
     */
    private String type;
    /**
     * The category of selectable thing.
     */
    private String category;

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
            result = dbContext.getThingDao().getAll();
        } else if (id > 0) {
            result.add(dbContext.getThingDao().getById(id));
        } else if (name != null) {
            result = dbContext.getThingDao().getByName(name);
        } else if (type != null) {
            result = dbContext.getThingDao().getByType(type);
        } else if (category != null) {
            CategoryDb categoryDb = dbContext.getCategoryDao().getByName(category);
            result.addAll(dbContext.getThingCategoryDao().getThingsByCategory(categoryDb.getId()));
        }
        for (ThingDb thingDb : result) {
            thingDb.setTypeDb(dbContext.getTypeDao().getById(thingDb.getTypeId()));
            thingDb.setGenderDb(dbContext.getGenderDao().getById(thingDb.getGenderId()));
            thingDb.setCategoryDbs(dbContext.getThingCategoryDao().getCategoriesByThing(thingDb.getId()));
            thingDb.setWeatherTypeDbs(dbContext.getThingWeatherTypeDao().getWeatherTypesByThing(thingDb.getId()));
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    /**
     * Sets the name of selectable thing.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type of selectable thing.
     *
     * @param type the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the category of selectable thing.
     *
     * @param category the category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
