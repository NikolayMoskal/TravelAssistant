package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.constant.DbConstants;
import by.neon.travelassistant.config.sqlite.model.AirportDb;

@Dao
public abstract class AirportDao extends BaseDao<AirportDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS)
    public abstract List<AirportDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_COLUMN_AIRPORT_NAME + " = :name")
    public abstract List<AirportDb> getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.ID + " = :id")
    public abstract AirportDb getById(long id);

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_CITIES_FK_COLUMN + " = :cityId")
    public abstract List<AirportDb> getByCity(long cityId);

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS)
    public abstract int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_COLUMN_AIRPORT_NAME + " = :name")
    public abstract int deleteByName(String name);

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.AIRPORTS_CITIES_FK_COLUMN + " = :cityId")
    public abstract int deleteByCity(long cityId);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_AIRPORTS + " SET " +
    DbConstants.AIRPORTS_COLUMN_AIRPORT_NAME + " = :name, " +
    DbConstants.AIRPORTS_COLUMN_LOCATION + " = :location, " +
    DbConstants.AIRPORTS_COLUMN_IATA_CODE + " = :iataCode, " +
    DbConstants.AIRPORTS_COLUMN_ICAO_CODE + " = :icaoCode WHERE " + DbConstants.ID + " = :id")
    public abstract int updateById(long id, String name, String location, String iataCode, String icaoCode);
}
