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

@Dao
public abstract class ThingCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(ThingCategoryDb... entities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(ThingCategoryDb... entities);

    @Query("SELECT " + DbConstants.TABLE_THINGS + ".* FROM " + DbConstants.TABLE_THINGS + "," + DbConstants.TABLE_THINGS_CATEGORIES +
            " WHERE " + DbConstants.TABLE_THINGS + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID +
            " AND " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID + " = :categoryId")
    public abstract List<ThingDb> getThingsByCategory(long categoryId);

    @Query("SELECT " + DbConstants.TABLE_CATEGORIES + ".* FROM " + DbConstants.TABLE_CATEGORIES + "," + DbConstants.TABLE_THINGS_CATEGORIES +
            " WHERE " + DbConstants.TABLE_CATEGORIES + "." + DbConstants.ID + " = " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID +
            " AND " + DbConstants.TABLE_THINGS_CATEGORIES + "." + DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID + " = :thingId")
    public abstract List<CategoryDb> getCategoriesByThing(long thingId);
}
