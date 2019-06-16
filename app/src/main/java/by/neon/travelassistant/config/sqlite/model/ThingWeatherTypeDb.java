package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The transition database entity between {@link ThingDb} and {@link WeatherTypeDb}.
 */
@Entity(tableName = DbConstants.TABLE_THINGS_WEATHER_TYPES,
        primaryKeys = {
                DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID,
                DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID
        },
        indices = {
                @Index(value = DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID)
        },
        foreignKeys = {
                @ForeignKey(entity = ThingDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = WeatherTypeDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        })
public class ThingWeatherTypeDb {
    /**
     * The ID of thing.
     */
    @ColumnInfo(name = DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID)
    private long thingId;
    /**
     * The ID of weather type.
     */
    @ColumnInfo(name = DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID)
    private long weatherTypeId;

    /**
     * Gets the ID of thing.
     *
     * @return the ID.
     */
    public long getThingId() {
        return thingId;
    }

    /**
     * Sets the ID of thing.
     *
     * @param thingId the ID to set.
     */
    public void setThingId(long thingId) {
        this.thingId = thingId;
    }

    /**
     * Gets the ID of weather type.
     *
     * @return the ID.
     */
    public long getWeatherTypeId() {
        return weatherTypeId;
    }

    /**
     * Sets the ID of weather type.
     *
     * @param weatherTypeId the ID to set.
     */
    public void setWeatherTypeId(long weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }
}
