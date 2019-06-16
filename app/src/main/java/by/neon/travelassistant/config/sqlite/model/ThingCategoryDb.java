package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The transition database entity between {@link ThingDb} and {@link CategoryDb}.
 */
@Entity(tableName = DbConstants.TABLE_THINGS_CATEGORIES,
        primaryKeys = {
                DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID,
                DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID
        },
        indices = {
                @Index(value = DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID)
        },
        foreignKeys = {
                @ForeignKey(entity = ThingDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = CategoryDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        })
public class ThingCategoryDb {
    /**
     * The ID of thing.
     */
    @ColumnInfo(name = DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID)
    private long thingId;
    /**
     * The ID of thing category.
     */
    @ColumnInfo(name = DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID)
    private long categoryId;

    /**
     * Gets the ID of thing.
     *
     * @return the ID.
     */
    public long getThingId() {
        return thingId;
    }

    /**
     * Sets the ID of thing.
     *
     * @param thingId the ID to set.
     */
    public void setThingId(long thingId) {
        this.thingId = thingId;
    }

    /**
     * Gets the ID of category.
     *
     * @return the ID.
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the ID of category.
     *
     * @param categoryId the ID to set.
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
