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
    @Query("DELETE FROM " + DbConstants.TABLE_CITIES)
    int deleteAll();
}
