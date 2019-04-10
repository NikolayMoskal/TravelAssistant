package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.location.Location;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.Airport;

@Dao
public interface AirportDao extends BaseDao<Airport> {
    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS)
    List<Airport> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE :expressions")
    List<Airport> getByQuery(String expressions);

    @Query("SELECT * FROM " + DbConstants.TABLE_AIRPORTS + " WHERE _id = :id")
    Airport getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS)
    int deleteAll();

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS + " WHERE :expressions")
    int deleteByQuery(String expressions);

    @Query("DELETE FROM " + DbConstants.TABLE_AIRPORTS + " WHERE " + DbConstants.ID + " = :id")
    int deleteById(long id);

    @Query("UPDATE " + DbConstants.TABLE_AIRPORTS + " SET " +
    DbConstants.AIRPORTS_COLUMN_AIRPORT_NAME + " = :name, " +
    DbConstants.AIRPORTS_COLUMN_LOCATION + " = :location, " +
    DbConstants.AIRPORTS_COLUMN_IATA_CODE + " = :iataCode, " +
    DbConstants.AIRPORTS_COLUMN_ICAO_CODE + " = :icaoCode WHERE " + DbConstants.ID + " = :id")
    int updateById(long id, String name, Location location, String iataCode, String icaoCode);
}
