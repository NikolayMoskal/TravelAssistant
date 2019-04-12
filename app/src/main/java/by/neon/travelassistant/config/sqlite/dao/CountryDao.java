package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.Country;
import by.neon.travelassistant.config.sqlite.select_model.CountryWithCities;

@Dao
public interface CountryDao extends BaseDao<Country> {
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES)
    List<Country> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE :expressions")
    List<Country> getByQuery(String expressions);

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.ID + " = :id")
    Country getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES)
    int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES + " WHERE :expressions")
    int deleteByQuery(String expressions);

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.ID + " = :id")
    int deleteById(long id);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_COUNTRIES + " SET " +
    DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME + " = :countryName, " +
    DbConstants.COUNTRIES_COLUMN_COUNTRY_CODE + " = :countryCode WHERE " + DbConstants.ID + " = :id")
    int updateById(long id, String countryName, String countryCode);

    @Transaction
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES)
    List<CountryWithCities> getAllWithRelations();

    @Transaction
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE :expressions")
    List<CountryWithCities> getByQueryWithRelations(String expressions);

    @Transaction
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.ID + " = :id")
    CountryWithCities getByIdWithRelations(long id);
}
