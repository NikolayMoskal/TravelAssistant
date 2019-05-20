package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_CATEGORIES)
public class CategoryDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_EN_US)
    private String categoryNameEn;
    @ColumnInfo(name = DbConstants.CATEGORIES_COLUMN_CATEGORY_NAME_RU_RU)
    private String categoryNameRu;

    public String getCategoryNameEn() {
        return categoryNameEn;
    }

    public void setCategoryNameEn(String categoryNameEn) {
        this.categoryNameEn = categoryNameEn;
    }

    public String getCategoryNameRu() {
        return categoryNameRu;
    }

    public void setCategoryNameRu(String categoryNameRu) {
        this.categoryNameRu = categoryNameRu;
    }
}
