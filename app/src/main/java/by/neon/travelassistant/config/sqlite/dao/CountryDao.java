package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.Country;

@Dao
public interface CountryDao extends BaseDao<Country> {
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES)
    List<Country> getAll();
    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES)
    int deleteAll();
}
