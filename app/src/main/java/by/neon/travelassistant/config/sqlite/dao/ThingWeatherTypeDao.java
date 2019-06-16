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

/**
 * The DAO class for {@link ThingWeatherTypeDb} model.
 */
@Dao
public abstract class ThingWeatherTypeDao {
    /**
     * Inserts many entities in database. Must have at least 1 entity to insert.
     *
     * @param entities the array of entities to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(ThingWeatherTypeDb... entities);

    /**
     * Updates many entities in database. Must have at least 1 entity to update.
     *
     * @param entities the array of replacements.
     * @return the count of updated entities.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(ThingWeatherTypeDb... entities);

    /**
     * Gets things by its weather type.
     *
     * @param weatherTypeId the existing weather type ID.
     * @return the list of things or empty list if not found.
     */
    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_THINGS_WEATHER_TYPES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_WEATHER_TYPES + "." + DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID +
            " AND " + DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID + " = :weatherTypeId")
    public abstract List<ThingDb> getThingsByWeatherType(long weatherTypeId);

    /**
     * Gets weather types by given thing ID.
     *
     * @param thingId the existing thing ID.
     * @return the list of weather types or empty list if not found.
     */
    @Query("SELECT " + DbConstants.TABLE_WEATHER_TYPES + ".* FROM " + DbConstants.TABLE_WEATHER_TYPES + "," + DbConstants.TABLE_THINGS_WEATHER_TYPES +
            " WHERE " + DbConstants.TABLE_WEATHER_TYPES + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_WEATHER_TYPES + "." + DbConstants.THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID +
            " AND " + DbConstants.THINGS_WEATHER_TYPES_COLUMN_THING_ID + " = :thingId")
    public abstract List<WeatherTypeDb> getWeatherTypesByThing(long thingId);
}
