package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingCategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

public final class ThingInsertAsyncTask extends AsyncTask<ThingDb, Void, List<Long>> {
    private static final String TAG = "ThingInsertAsyncTask";

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
    protected List<Long> doInBackground(ThingDb... thingDbs) {
        if (thingDbs == null || thingDbs.length == 0) {
            throw new IllegalArgumentException("No present things to insert. Must be at least 1 thing.");
        }

        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<Long> result = dbContext.getThingDao().insert(thingDbs);
        int resultSize = result == null ? 0 : result.size();
        Log.i(TAG, "doInBackground: " + resultSize + " rows inserted.");
        if (result == null) {
            return new ArrayList<>(0);
        }
        for (int index = 0; index < resultSize; index++) {
            List<CategoryDb> categoryDbs = dbContext.getCategoryDao()
                    .getByNames(getNames(thingDbs[index].getCategoryDbs()));
            List<ThingCategoryDb> list = new ArrayList<>(0);
            for (CategoryDb categoryDb : categoryDbs) {
                ThingCategoryDb entity = new ThingCategoryDb();
                entity.setCategoryId(categoryDb.getId());
                entity.setThingId(result.get(index));
                list.add(entity);
            }
            dbContext.getThingCategoryDao().insert(list.toArray(new ThingCategoryDb[0]));
        }
        return result;
    }

    private List<String> getNames(List<CategoryDb> categoryDbs) {
        List<String> names = new ArrayList<>(0);
        for (CategoryDb categoryDb : categoryDbs) {
            names.add(categoryDb.getCategoryName());
        }
        return names;
    }
}
