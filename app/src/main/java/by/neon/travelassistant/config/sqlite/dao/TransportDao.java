package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO for {@link TransportDb} model.
 */
@Dao
public abstract class TransportDao extends BaseDao<TransportDb> {
    /**
     * Gets all transports.
     *
     * @return the list of transports.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TRANSPORTS)
    public abstract List<TransportDb> getAll();

    /**
     * Gets transport by its name.
     *
     * @param name the name of transport.
     * @return the transport model or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TRANSPORTS + " WHERE " + DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_EN_US + " = :name LIMIT 1")
    public abstract TransportDb getByName(String name);

    /**
     * Gets the transports by their names.
     *
     * @param names the list of transport names.
     * @return the list of transports or empty list if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TRANSPORTS + " WHERE " + DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_EN_US + " IN (:names)")
    public abstract List<TransportDb> getByNames(List<String> names);
}
