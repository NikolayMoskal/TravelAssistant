package by.neon.travelassistant.activity.query;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public final class DownloadWeatherIconAsyncTask extends AsyncTask<Void, Void, Drawable> {
    private static final String TAG = "DownloadWeatherIcon";
    private String url;

    public DownloadWeatherIconAsyncTask(String url) {
        this.url = url;
    }

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
    protected Drawable doInBackground(Void... voids) {
        Drawable bitmap = null;
        try (InputStream stream = (InputStream) new URL(url).getContent()) {
            return Drawable.createFromStream(stream, "icon");
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage(), e);
        }
        return bitmap;
    }
}
