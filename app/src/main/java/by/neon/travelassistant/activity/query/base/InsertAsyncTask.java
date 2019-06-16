package by.neon.travelassistant.activity.query.base;

import android.os.AsyncTask;

import java.util.List;

/**
 * Represents a simple async task for insert the objects of given type into database.
 *
 * @param <T> the database model type that indicates the appropriate table in database.
 */
public abstract class InsertAsyncTask<T> extends AsyncTask<T, Void, List<Long>> {
}
