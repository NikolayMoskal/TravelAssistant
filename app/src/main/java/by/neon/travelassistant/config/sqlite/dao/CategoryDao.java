package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.constant.DbConstants;

@Dao
public abstract class CategoryDao extends BaseDao<CategoryDb> {
    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES)
    public abstract List<CategoryDb> getAll();

    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME + " = :name LIMIT 1")
    public abstract CategoryDb getByName(String name);

    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME + " IN (:names)")
    public abstract List<CategoryDb> getByNames(List<String> names);

    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract CategoryDb getById(long id);
}
