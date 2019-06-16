package by.neon.travelassistant.activity.query.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.activity.query.base.SelectAsyncTask;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.TransportDb;

public final class TransportSelectAsyncTask extends SelectAsyncTask<TransportDb> {
    private static final String TAG = "TransportSelect";
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
    protected List<TransportDb> doInBackground(Void... voids) {
        TravelDbContext dbContext = Startup.getStartup().getDbContext();
        List<TransportDb> result = new ArrayList<>(0);
        if (isSelectAll) {
            result.addAll(dbContext.getTransportDao().getAll());
        } else if (names != null && names.size() > 0) {
            if (names.size() > 1) {
                result.addAll(dbContext.getTransportDao().getByNames(names));
            } else {
                result.add(dbContext.getTransportDao().getByName(names.get(0)));
            }
        }
        Log.i(TAG, "doInBackground: " + result.size() + " rows returned.");
        return result;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
