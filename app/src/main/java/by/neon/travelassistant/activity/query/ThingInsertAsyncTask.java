package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.ThingCategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.ThingWeatherTypeDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

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
        for (ThingDb thingDb : thingDbs) {
            TypeDb typeDb = dbContext.getTypeDao().getByName(thingDb.getTypeDb().getTypeNameEn());
            GenderDb genderDb = dbContext.getGenderDao().getByType(thingDb.getGenderDb().getTypeEn());
            thingDb.setTypeId(typeDb.getId());
            thingDb.setGenderId(genderDb.getId());
        }

        List<Long> result = dbContext.getThingDao().insert(thingDbs);
        int resultSize = result == null ? 0 : result.size();
        Log.i(TAG, "doInBackground: " + resultSize + " rows inserted.");
        if (result == null) {
            return new ArrayList<>(0);
        }
        for (int index = 0; index < resultSize; index++) {
            List<CategoryDb> categoryDbs = dbContext.getCategoryDao()
                    .getByNames(getCategoryNames(thingDbs[index].getCategoryDbs()));
            List<ThingCategoryDb> list = new ArrayList<>(0);
            for (CategoryDb categoryDb : categoryDbs) {
                ThingCategoryDb entity = new ThingCategoryDb();
                entity.setCategoryId(categoryDb.getId());
                entity.setThingId(result.get(index));
                list.add(entity);
            }
            dbContext.getThingCategoryDao().insert(list.toArray(new ThingCategoryDb[0]));
            List<WeatherTypeDb> weatherTypeDbs = dbContext.getWeatherTypeDao()
                    .getByNames(getWeatherTypeNames(thingDbs[index].getWeatherTypeDbs()));
            List<ThingWeatherTypeDb> list1 = new ArrayList<>(0);
            for (WeatherTypeDb weatherTypeDb : weatherTypeDbs) {
                ThingWeatherTypeDb entity = new ThingWeatherTypeDb();
                entity.setThingId(result.get(index));
                entity.setWeatherTypeId(weatherTypeDb.getId());
                list1.add(entity);
            }
            dbContext.getThingWeatherTypeDao().insert(list1.toArray(new ThingWeatherTypeDb[0]));
        }
        return result;
    }

    private List<String> getCategoryNames(List<CategoryDb> categoryDbs) {
        List<String> names = new ArrayList<>(0);
        for (CategoryDb categoryDb : categoryDbs) {
            names.add(categoryDb.getCategoryNameEn());
        }
        return names;
    }

    private List<String> getWeatherTypeNames(List<WeatherTypeDb> weatherTypeDbs) {
        List<String> names = new ArrayList<>(0);
        for (WeatherTypeDb weatherTypeDb : weatherTypeDbs) {
            names.add(weatherTypeDb.getType());
        }
        return names;
    }
}
