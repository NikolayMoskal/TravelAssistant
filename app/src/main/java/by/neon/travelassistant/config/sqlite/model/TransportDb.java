package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_TRANSPORTS)
public class TransportDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME)
    private String name;
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_PACK_WEIGHT)
    private double packWeight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }
}
