package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.config.sqlite.model.CountryDb;

@Dao
public abstract class CountryDao extends BaseDao<CountryDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES)
    public abstract List<CountryDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME + " = :name")
    public abstract List<CountryDb> getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract CountryDb getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES)
    public abstract int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME + " = :name")
    public abstract int deleteByName(String name);

    @Query("DELETE FROM " + DbConstants.TABLE_COUNTRIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_COUNTRIES + " SET " +
    DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME + " = :countryName, " +
    DbConstants.COUNTRIES_COLUMN_COUNTRY_CODE + " = :countryCode WHERE " + DbConstants.ID + " = :id")
    public abstract int updateById(long id, String countryName, String countryCode);

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.CITIES_COUNTRIES_FK_COLUMN + " = :countryId")
    abstract List<CityDb> getCities(long countryId);

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_CITIES_FK_COLUMN + " = :cityId")
    abstract List<AirportDb> getAirports(long cityId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract long insertCountry(CountryDb countryDb);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertCities(List<CityDb> cities);

    public long insertCountryWithCities(CountryDb countryDb) {
        insertCities(countryDb.getCities());
        return insertCountry(countryDb);
    }

    public CountryDb getCountryWithCitiesById(long id) {
        CountryDb countryDb = getById(id);
        setRelations(countryDb);
        return countryDb;
    }

    public List<CountryDb> getCountriesWithCitiesByName(String name) {
        List<CountryDb> countries = getByName(name);
        for (CountryDb countryDb : countries) {
            setRelations(countryDb);
        }
        return countries;
    }

    public List<CountryDb> getAllCountriesWithCities() {
        List<CountryDb> countries = getAll();
        for (CountryDb countryDb : countries) {
            setRelations(countryDb);
        }
        return countries;
    }

    private void setRelations(CountryDb countryDb) {
        List<CityDb> cities = getCities(countryDb.getId());
        for (CityDb cityDb : cities) {
            cityDb.setCountryDb(countryDb);
            cityDb.setAirportDbs(getAirports(cityDb.getId()));
        }
        countryDb.setCities(cities);
    }
}
