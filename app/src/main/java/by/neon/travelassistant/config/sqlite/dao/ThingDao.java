package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class ThingDao extends BaseDao<ThingDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS)
    public abstract List<ThingDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name")
    public abstract List<ThingDb> getByName(String name);

    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_TYPES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.THINGS_TYPES_FK_COLUMN + " = " + DbConstants.TABLE_TYPES + "." + DbConstants.ID +
            " AND " + DbConstants.TABLE_TYPES + "." + DbConstants.TYPES_COLUMN_TYPE_NAME_EN_US + " = :type")
    public abstract List<ThingDb> getByType(String type);

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract ThingDb getById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name")
    public abstract int deleteByName(String name);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS)
    public abstract int deleteAll();
}
