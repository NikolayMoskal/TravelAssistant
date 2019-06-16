package by.neon.travelassistant.activity.query.base;

import android.os.AsyncTask;

/**
 * Represents a simple async task for update the objects of given type into database.
 *
 * @param <T> the database model type that indicates the appropriate table in database.
 */
public abstract class UpdateAsyncTask<T> extends AsyncTask<T, Void, Integer> {
}
