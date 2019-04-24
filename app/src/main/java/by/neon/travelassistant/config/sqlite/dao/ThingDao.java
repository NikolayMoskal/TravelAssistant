package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.constant.DbConstants;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

@Dao
public abstract class ThingDao extends BaseDao<ThingDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS)
    public abstract List<ThingDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name OR " + DbConstants.THINGS_COLUMN_THING_NAME_RU_RU + " = :name")
    public abstract List<ThingDb> getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_TYPE + " = :type")
    public abstract List<ThingDb> getByType(String type);

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_CATEGORY + " = :category")
    public abstract List<ThingDb> getByCategory(String category);

    @Query("SELECT * FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract ThingDb getById(long id);

    @Query("UPDATE OR IGNORE " + DbConstants.TABLE_THINGS + " SET " +
            DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :nameEn, " +
            DbConstants.THINGS_COLUMN_THING_NAME_RU_RU + " = :nameRu, " +
            DbConstants.THINGS_COLUMN_TYPE + " = :type, " +
            DbConstants.THINGS_COLUMN_CATEGORY + " = :category WHERE " + DbConstants.ID + " = :id")
    public abstract int updateById(long id, String nameEn, String nameRu, String type, String category);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.ID + " = :id")
    public abstract int deleteById(long id);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_THING_NAME_EN_US + " = :name OR " + DbConstants.THINGS_COLUMN_THING_NAME_RU_RU + " = :name")
    public abstract int deleteByName(String name);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_TYPE + " = :type")
    public abstract int deleteByType(String type);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS + " WHERE " + DbConstants.THINGS_COLUMN_CATEGORY + " = :category")
    public abstract int deleteByCategory(String category);

    @Query("DELETE FROM " + DbConstants.TABLE_THINGS)
    public abstract int deleteAll();
}
