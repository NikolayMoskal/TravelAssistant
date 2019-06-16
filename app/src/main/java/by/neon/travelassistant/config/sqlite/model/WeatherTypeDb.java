package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The database entity for weather type.
 */
@Entity(tableName = DbConstants.TABLE_WEATHER_TYPES)
public class WeatherTypeDb extends BaseDbEntity {
    /**
     * The type of weather, e.g. rain, snow, warm etc.
     */
    @ColumnInfo(name = DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME)
    private String type;

    /**
     * Gets the type of weather.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of weather.
     *
     * @param type the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
