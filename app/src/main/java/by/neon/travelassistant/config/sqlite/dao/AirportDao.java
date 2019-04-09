package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

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
}
