package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class GenderDao extends BaseDao<GenderDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS)
    public abstract List<GenderDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS + " WHERE " + DbConstants.GENDERS_COLUMN_GENDER_TYPE_EN_US + " = :type LIMIT 1")
    public abstract GenderDb getByType(String type);

    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS + " WHERE " + DbConstants.ID + " = :id")
    public abstract GenderDb getById(long id);
}
