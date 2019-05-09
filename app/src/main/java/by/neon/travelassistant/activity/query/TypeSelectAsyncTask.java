package by.neon.travelassistant.activity.query;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.TypeDb;

public final class TypeSelectAsyncTask extends AsyncTask<Void, Void, List<TypeDb>> {
    private static final String TAG = "TypeSelectAsyncTask";
    private boolean isSelectAll;
    private long id;
    private String name;

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
    protected List<TypeDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<TypeDb> result = new ArrayList<>(0);
        if (id > 0) {
            result.add(dbContext.getTypeDao().getById(id));
        } else if (name != null) {
            result.add(dbContext.getTypeDao().getByName(name));
        } else if (isSelectAll) {
            result.addAll(dbContext.getTypeDao().getAll());
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }
}
