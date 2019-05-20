package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.ThingWeatherTypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class ThingWeatherTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(ThingWeatherTypeDb... entities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(ThingWeatherTypeDb... entities);

    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_THINGS_WEATHER_TYPES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_WEATHER_TYPES + "." + DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID +
            " AND " + DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID + " = :weatherTypeId")
    public abstract List<ThingDb> getThingsByWeatherType(long weatherTypeId);

    @Query("SELECT " + DbConstants.TABLE_WEATHER_TYPES + ".* FROM " + DbConstants.TABLE_WEATHER_TYPES + "," + DbConstants.TABLE_THINGS_WEATHER_TYPES +
            " WHERE " + DbConstants.TABLE_WEATHER_TYPES + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_WEATHER_TYPES + "." + DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID +
            " AND " + DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID + " = :thingId")
    public abstract List<WeatherTypeDb> getWeatherTypesByThing(long thingId);
}
