package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.constant.DbConstants;

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
    @ColumnInfo(name = DbConstants.THINGS_CATEGORIES_COLUMN_THING_ID)
    private long thingId;
    @ColumnInfo(name = DbConstants.THINGS_CATEGORIES_COLUMN_CATEGORY_ID)
    private long categoryId;

    public long getThingId() {
        return thingId;
    }

    public void setThingId(long thingId) {
        this.thingId = thingId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
