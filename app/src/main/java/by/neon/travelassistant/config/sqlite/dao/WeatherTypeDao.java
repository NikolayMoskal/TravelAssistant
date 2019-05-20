package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class WeatherTypeDao extends BaseDao<WeatherTypeDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES)
    public abstract List<WeatherTypeDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME + " = :name LIMIT 1")
    public abstract WeatherTypeDb getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.WEATHER_TYPES_COLUMN_TYPE_NAME + " IN (:names)")
    public abstract List<WeatherTypeDb> getByNames(List<String> names);

    @Query("SELECT * FROM " + DbConstants.TABLE_WEATHER_TYPES + " WHERE " + DbConstants.ID + " = :id")
    public abstract WeatherTypeDb getById(long id);
}
