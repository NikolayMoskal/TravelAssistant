package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The database entity for thing category.
 */
@Entity(tableName = DbConstants.TABLE_CATEGORIES)
public class CategoryDb extends BaseDbEntity {
    /**
     * The name of category in en-US locale.
     */
    @ColumnInfo(name = DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_EN_US)
    private String categoryNameEn;
    /**
     * The name of category in ru-RU locale.
     */
    @ColumnInfo(name = DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_RU_RU)
    private String categoryNameRu;

    /**
     * Gets the english name of category.
     *
     * @return the name.
     */
    public String getCategoryNameEn() {
        return categoryNameEn;
    }

    /**
     * Sets the english name of category.
     *
     * @param categoryNameEn the name to set.
     */
    public void setCategoryNameEn(String categoryNameEn) {
        this.categoryNameEn = categoryNameEn;
    }

    /**
     * Gets the russian name of category.
     *
     * @return the name.
     */
    public String getCategoryNameRu() {
        return categoryNameRu;
    }

    /**
     * Sets the russian name of category.
     *
     * @param categoryNameRu the name to set.
     */
    public void setCategoryNameRu(String categoryNameRu) {
        this.categoryNameRu = categoryNameRu;
    }
}
