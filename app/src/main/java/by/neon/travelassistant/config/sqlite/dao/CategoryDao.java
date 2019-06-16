package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO class for {@link CategoryDb} model.
 */
@Dao
public abstract class CategoryDao extends BaseDao<CategoryDb> {
    /**
     * Gets all categories.
     *
     * @return the list of categories.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES)
    public abstract List<CategoryDb> getAll();

    /**
     * Gets the category by given name.
     *
     * @param name the category name.
     * @return the category or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_EN_US + " = :name LIMIT 1")
    public abstract CategoryDb getByName(String name);

    /**
     * Gets the many categories by its names.
     *
     * @param names the names of categories.
     * @return the list of categories or empty list if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_EN_US + " IN (:names)")
    public abstract List<CategoryDb> getByNames(List<String> names);

    /**
     * Gets the single category by its ID.
     *
     * @param id the ID of category record in database.
     * @return the category or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_CATEGORIES + " WHERE " + DbConstants.ID + " = :id")
    public abstract CategoryDb getById(long id);
}
