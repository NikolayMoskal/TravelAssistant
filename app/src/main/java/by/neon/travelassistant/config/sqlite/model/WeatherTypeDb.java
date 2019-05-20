package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_WEATHER_TYPES)
public class WeatherTypeDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
