package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.City;

@Dao
public interface CityDao extends BaseDao<City> {
    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES)
    List<City> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE :expressions")
    List<City> getByQuery(String expressions);

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE _id = :id")
    City getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES)
    int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES + " WHERE :expressions")
    int deleteByQuery(String expressions);

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.ID + " = :id")
    int deleteById(long id);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_CITIES + " SET " +
    DbConstants.CITIES_COLUMN_CITY_CODE + " = :cityCode, " +
    DbConstants.CITIES_COLUMN_CITY_NAME + " = :cityName WHERE " + DbConstants.ID + " = :id")
    int updateById(long id, String cityCode, String cityName);
}
