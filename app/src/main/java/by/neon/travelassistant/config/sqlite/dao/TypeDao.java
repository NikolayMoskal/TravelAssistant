package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class TypeDao extends BaseDao<TypeDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES)
    public abstract List<TypeDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES + " WHERE " + DbConstants.ID + " = :id")
    public abstract TypeDb getById(long id);

    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES + " WHERE " + DbConstants.TYPES_COLUMN_TYPE_NAME + " = :name LIMIT 1")
    public abstract TypeDb getByName(String name);
}
