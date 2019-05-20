package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_GENDERS)
public class GenderDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.GENDERS_COLUMN_GENDER_TYPE_EN_US)
    private String typeEn;
    @ColumnInfo(name = DbConstants.GENDERS_COLUMN_GENDER_TYPE_RU_RU)
    private String typeRu;

    public String getTypeEn() {
        return typeEn;
    }

    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    public String getTypeRu() {
        return typeRu;
    }

    public void setTypeRu(String typeRu) {
        this.typeRu = typeRu;
    }
}
