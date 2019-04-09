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

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE :expressions")
    List<Country> getByQuery(String expressions);

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE _id = :id")
    Country getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES)
    int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES + " WHERE :expressions")
    int deleteByQuery(String expressions);
}
