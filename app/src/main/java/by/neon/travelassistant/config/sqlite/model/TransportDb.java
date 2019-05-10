package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_TRANSPORTS)
public class TransportDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_EN_US)
    private String nameEn;
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_RU_RU)
    private String nameRu;
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_PACK_WEIGHT)
    private double packWeight;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String name) {
        this.nameEn = name;
    }

    public double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }
}
