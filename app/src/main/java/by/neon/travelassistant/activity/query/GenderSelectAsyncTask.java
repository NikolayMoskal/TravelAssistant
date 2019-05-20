package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.GenderDb;

public final class GenderSelectAsyncTask extends AsyncTask<Void, Void, List<GenderDb>> {
    private static final String TAG = "GenderSelectAsyncTask";
    private boolean isSelectAll;
    private long id;
    private List<String> types;

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
    protected List<GenderDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<GenderDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result.addAll(dbContext.getGenderDao().getAll());
        } else if (id > 0) {
            result.add(dbContext.getGenderDao().getById(id));
        } else if (types != null && types.size() > 0) {
            if (types.size() > 1) {
                result.addAll(dbContext.getGenderDao().getByTypes(types));
            } else {
                result.add(dbContext.getGenderDao().getByType(types.get(0)));
            }
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

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
