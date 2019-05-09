package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.constant.DbConstants;

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
    @ColumnInfo(name = DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID)
    private long thingId;
    @ColumnInfo(name = DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID)
    private long weatherTypeId;

    public long getThingId() {
        return thingId;
    }

    public void setThingId(long thingId) {
        this.thingId = thingId;
    }

    public long getWeatherTypeId() {
        return weatherTypeId;
    }

    public void setWeatherTypeId(long weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }
}
