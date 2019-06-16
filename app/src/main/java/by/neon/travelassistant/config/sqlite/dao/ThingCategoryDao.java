package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingCategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO for {@link ThingCategoryDb} model.
 */
@Dao
public abstract class ThingCategoryDao {
    /**
     * Inserts the many entities in database.
     *
     * @param entities the array of entities to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(ThingCategoryDb... entities);

    /**
     * Updates the many entities in database.
     *
     * @param entities the array of replacements each of which has the ID.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(ThingCategoryDb... entities);

    /**
     * Gets the things by given category ID.
     *
     * @param categoryId the existing category ID.
     * @return the list of things or empty list if not found.
     */
    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_THINGS_CATEGORIES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID +
            " AND " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID + " = :categoryId")
    public abstract List<ThingDb> getThingsByCategory(long categoryId);

    /**
     * Gets the list of categories by given thing ID.
     *
     * @param thingId the existing thing ID.
     * @return the list of categories or empty list if not found.
     */
    @Query("SELECT " + DbConstants.TABLE_CATEGORIES + ".* FROM " + DbConstants.TABLE_CATEGORIES + "," + DbConstants.TABLE_THINGS_CATEGORIES +
            " WHERE " + DbConstants.TABLE_CATEGORIES + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID +
            " AND " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID + " = :thingId")
    public abstract List<CategoryDb> getCategoriesByThing(long thingId);
}
