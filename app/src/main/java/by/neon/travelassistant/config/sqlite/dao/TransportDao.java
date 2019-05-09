package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class TransportDao extends BaseDao<TransportDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_TRANSPORTS)
    public abstract List<TransportDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_TRANSPORTS + " WHERE " + DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME + " = :name")
    public abstract List<TransportDb> getByName(String name);
}
