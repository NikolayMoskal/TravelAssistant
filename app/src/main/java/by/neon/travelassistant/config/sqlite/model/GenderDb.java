package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_GENDERS)
public class GenderDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.GENDERS_COLUMN_GENDER_TYPE)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
