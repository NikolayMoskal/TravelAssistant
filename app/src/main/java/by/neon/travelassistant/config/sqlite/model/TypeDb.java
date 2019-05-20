package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_TYPES)
public class TypeDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.TYPES_COLUMN_TYPE_NAME_EN_US)
    private String typeNameEn;
    @ColumnInfo(name = DbConstants.TYPES_COLUMN_TYPE_NAME_RU_RU)
    private String typeNameRu;

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeName) {
        this.typeNameEn = typeName;
    }

    public String getTypeNameRu() {
        return typeNameRu;
    }

    public void setTypeNameRu(String typeNameRu) {
        this.typeNameRu = typeNameRu;
    }
}
