package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The database entity for gender.
 */
@Entity(tableName = DbConstants.TABLE_GENDERS)
public class GenderDb extends BaseDbEntity {
    /**
     * The gender type in en-US locale.
     */
    @ColumnInfo(name = DbConstants.GENDERS_COLUMN_GENDER_TYPE_EN_US)
    private String typeEn;
    /**
     * The gender type in ru-RU locale.
     */
    @ColumnInfo(name = DbConstants.GENDERS_COLUMN_GENDER_TYPE_RU_RU)
    private String typeRu;

    /**
     * Gets the gender type (english locale).
     *
     * @return the gender type.
     */
    public String getTypeEn() {
        return typeEn;
    }

    /**
     * Sets the gender type.
     *
     * @param typeEn the type to set.
     */
    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    /**
     * Gets the gender type (russian locale).
     *
     * @return the gender type.
     */
    public String getTypeRu() {
        return typeRu;
    }

    /**
     * Sets the gender type.
     *
     * @param typeRu the type to set.
     */
    public void setTypeRu(String typeRu) {
        this.typeRu = typeRu;
    }
}
