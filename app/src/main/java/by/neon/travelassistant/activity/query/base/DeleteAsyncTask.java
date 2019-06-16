package by.neon.travelassistant.activity.query.base;

import android.os.AsyncTask;

/**
 * Represents a simple async task for delete the objects of given type from database.
 *
 * @param <T> the database model type that indicates the appropriate table in database.
 */
public abstract class DeleteAsyncTask<T> extends AsyncTask<T, Void, Integer> {
    /**
     * Indicates that all records of given type should be deleted from database.
     */
    protected boolean isDeleteAll;
    /**
     * The unique row ID of deletable record in table.
     */
    protected long id;

    /**
     * Sets the indicator for delete all records from database.
     *
     * @param deleteAll the value to set.
     */
    public void setDeleteAll(boolean deleteAll) {
        isDeleteAll = deleteAll;
    }

    /**
     * Sets the unique row ID to delete only one record by this ID.
     *
     * @param id the ID to set (must be present as a natural number).
     */
    public void setId(long id) {
        this.id = id;
    }
}
