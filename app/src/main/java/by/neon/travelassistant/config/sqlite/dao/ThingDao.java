package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO class for {@link ThingDb} model.
 */
@Dao
public abstract class ThingDao extends BaseDao<ThingDb> {
    /**
     * Gets all things.
     *
     * @return the list of things.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS)
    public abstract List<ThingDb> getAll();

    /**
     * Gets things by given name.
     *
     * @param name the name of thing.
     * @return the list of things or empty list if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name")
    public abstract List<ThingDb> getByName(String name);

    /**
     * Gets the things by their type.
     *
     * @param type the type of thing.
     * @return the list of things or empty list if not found.
     */
    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_TYPES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.THINGS_TYPES_FK_COLUMN + " = " + DbConstants.TABLE_TYPES + "." + DbConstants.ID +
            " AND " + DbConstants.TABLE_TYPES + "." + DbConstants.TYPES_COLUMN_TYPE_NAME_EN_US + " = :type")
    public abstract List<ThingDb> getByType(String type);

    /**
     * Gets the thing by its ID.
     *
     * @param id the ID of thing record in database.
     * @return the thing or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract ThingDb getById(long id);

    /**
     * Removes the thing by its ID.
     *
     * @param id the ID of thing record in database.
     * @return the number 1 if removed successfully or 0 if not.
     */
    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    /**
     * Removes the things by their name.
     *
     * @param name the name of removable things.
     * @return the count of removed things.
     */
    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name")
    public abstract int deleteByName(String name);

    /**
     * Removes all things.
     *
     * @return the count of removed things.
     */
    @Query("DELETE FROM " + DbConstants.TABLE_THINGS)
    public abstract int deleteAll();
}
