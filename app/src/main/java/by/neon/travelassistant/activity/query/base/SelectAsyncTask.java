package by.neon.travelassistant.activity.query.base;

import android.os.AsyncTask;

import java.util.List;

/**
 * Represents a simple async task for select the objects of given type from database.
 *
 * @param <T> the database model type that indicates the appropriate table in database.
 */
public abstract class SelectAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {
    /**
     * Indicates that all records of given type should be selected from database.
     */
    protected boolean isSelectAll;
    /**
     * The unique row ID of selectable record in table.
     */
    protected long id;

    /**
     * Sets the indicator for select all records from database.
     *
     * @param selectAll the value to set.
     */
    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }

    /**
     * Sets the unique row ID to select only one record.
     *
     * @param id the ID to set (must be present as a natural number).
     */
    public void setId(long id) {
        this.id = id;
    }
}
