package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import by.neon.travelassistant.constant.DbConstants;

public abstract class BaseDbEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.ID)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
