package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO class for {@link WeatherTypeDb} model.
 */
@Dao
public abstract class WeatherTypeDao extends BaseDao<WeatherTypeDb> {
    /**
     * Gets all weather types.
     *
     * @return the list of weather types.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES)
    public abstract List<WeatherTypeDb> getAll();

    /**
     * Gets weather type by its name.
     *
     * @param name the weather type name.
     * @return the weather type or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME + " = :name LIMIT 1")
    public abstract WeatherTypeDb getByName(String name);

    /**
     * Gets the list of weather types by given names.
     *
     * @param names the list of weather type names.
     * @return the list of thing types or empty list if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME + " IN (:names)")
    public abstract List<WeatherTypeDb> getByNames(List<String> names);

    /**
     * Gets the weather type by its ID.
     *
     * @param id the ID of the weather type record in database.
     * @return the weather type or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.ID + " = :id")
    public abstract WeatherTypeDb getById(long id);
}
