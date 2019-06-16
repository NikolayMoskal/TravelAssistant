package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import by.neon.travelassistant.constant.DbConstants;

/**
 * Base database entity.
 */
public abstract class BaseDbEntity {
    /**
     * The ID of this entity.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.ID)
    private long id;

    /**
     * Gets the ID.
     *
     * @return the ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID to set.
     */
    public void setId(long id) {
        this.id = id;
    }
}
