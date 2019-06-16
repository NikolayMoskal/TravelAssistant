package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The database entity for thing type.
 */
@Entity(tableName = DbConstants.TABLE_TYPES)
public class TypeDb extends BaseDbEntity {
    /**
     * The name of thing type in en-US locale.
     */
    @ColumnInfo(name = DbConstants.TYPES_COLUMN_TYPE_NAME_EN_US)
    private String typeNameEn;
    /**
     * The name of thing type in ru-RU locale.
     */
    @ColumnInfo(name = DbConstants.TYPES_COLUMN_TYPE_NAME_RU_RU)
    private String typeNameRu;

    /**
     * Gets the name of thing type in en-US locale.
     *
     * @return the name.
     */
    public String getTypeNameEn() {
        return typeNameEn;
    }

    /**
     * Sets the name of thing type in en-US locale.
     *
     * @param typeName the name to set.
     */
    public void setTypeNameEn(String typeName) {
        this.typeNameEn = typeName;
    }

    /**
     * Gets the name of thing type in ru-RU locale.
     *
     * @return the name.
     */
    public String getTypeNameRu() {
        return typeNameRu;
    }

    /**
     * Sets the name of thing type in ru-RU locale.
     *
     * @param typeNameRu the name to set.
     */
    public void setTypeNameRu(String typeNameRu) {
        this.typeNameRu = typeNameRu;
    }
}
