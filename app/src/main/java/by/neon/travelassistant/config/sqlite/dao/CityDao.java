package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import by.neon.travelassistant.constant.DbConstants;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;

@Dao
public abstract class CityDao extends BaseDao<CityDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES)
    public abstract List<CityDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.CITIES_COLUMN_CITY_NAME + " = :name")
    public abstract List<CityDb> getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract CityDb getById(long id);

    @Query("SELECT * FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.CITIES_COUNTRIES_FK_COLUMN + " = :countryId")
    public abstract List<CityDb> getByCountry(long countryId);

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES)
    public abstract int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.CITIES_COLUMN_CITY_NAME + " = :name")
    public abstract int deleteByName(String name);

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.CITIES_COUNTRIES_FK_COLUMN + " = :countryId")
    public abstract int deleteByCountry(long countryId);

    @Query("DELETE FROM " + DbConstants.TABLE_CITIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_CITIES + " SET " +
    DbConstants.CITIES_COLUMN_CITY_CODE + " = :cityCode, " +
    DbConstants.CITIES_COLUMN_CITY_NAME + " = :cityName WHERE " + DbConstants.ID + " = :id")
    public abstract int updateById(long id, String cityCode, String cityName);

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_CITIES_FK_COLUMN + " = :cityId")
    abstract List<AirportDb> getAirports(long cityId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract long insertCity(CityDb cityDb);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertAirports(List<AirportDb> airportDbs);

    public long insertCityWithAirports(CityDb cityDb) {
        insertAirports(cityDb.getAirportDbs());
        return insertCity(cityDb);
    }

    public List<CityDb> getAllCitiesWithAirports() {
        List<CityDb> cities = getAll();
        for (CityDb cityDb : cities) {
            setRelations(cityDb);
        }
        return cities;
    }

    public List<CityDb> getCitiesWithAirportsByName(String name) {
        List<CityDb> cities = getByName(name);
        for (CityDb cityDb : cities) {
            setRelations(cityDb);
        }
        return cities;
    }

    public CityDb getCityWithAirportsById(long id) {
        CityDb cityDb = getById(id);
        setRelations(cityDb);
        return cityDb;
    }

    private void setRelations(CityDb cityDb) {
        List<AirportDb> airportDbs = getAirports(cityDb.getId());
        for (AirportDb airportDb : airportDbs) {
            airportDb.setCityDb(cityDb);
        }
        cityDb.setAirportDbs(airportDbs);
    }
}
