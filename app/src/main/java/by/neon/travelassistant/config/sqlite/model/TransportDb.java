package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

/**
 * The database entity for transport.
 */
@Entity(tableName = DbConstants.TABLE_TRANSPORTS)
public class TransportDb extends BaseDbEntity {
    /**
     * The name of transport (english locale).
     */
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_EN_US)
    private String nameEn;
    /**
     * The name of transport (russian locale).
     */
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_TRANSPORT_NAME_RU_RU)
    private String nameRu;
    /**
     * The max weight of hand luggage for this transport.
     */
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_HAND_PACK_WEIGHT)
    private double packWeight;
    /**
     * The max weight of whole luggage for this transport.
     */
    @ColumnInfo(name = DbConstants.TRANSPORTS_COLUMN_MAX_PACK_WEIGHT)
    private double maxWeight;

    /**
     * Gets the name of transport in en-US locale.
     *
     * @return the name.
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * Sets the name of transport in en-US locale.
     *
     * @param name the name to set.
     */
    public void setNameEn(String name) {
        this.nameEn = name;
    }

    /**
     * Gets the max weight of hand luggage for this transport.
     *
     * @return the weight.
     */
    public double getPackWeight() {
        return packWeight;
    }

    /**
     * Sets the max weight of hand luggage for this transport.
     *
     * @param packWeight the weight to set.
     */
    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }

    /**
     * Gets the name of transport in ru-RU locale.
     *
     * @return the name.
     */
    public String getNameRu() {
        return nameRu;
    }

    /**
     * Sets the name of transport in ru-RU locale.
     *
     * @param nameRu the name to set.
     */
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    /**
     * Gets the max weight of whole luggage for this transport.
     *
     * @return the weight.
     */
    public double getMaxWeight() {
        return maxWeight;
    }

    /**
     * Sets the max weight of whole luggage for this transport.
     *
     * @param maxWeight the weight to set.
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
