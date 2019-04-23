package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_THINGS)
public class ThingDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_EN_US)
    private String thingNameEn;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_RU_RU)
    private String thingNameRu;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_TYPE)
    private String type;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_CATEGORY)
    private String category;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_WEIGHT)
    private double weight;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getThingNameEn() {
        return thingNameEn;
    }

    public void setThingNameEn(String thingNameEn) {
        this.thingNameEn = thingNameEn;
    }

    public String getThingNameRu() {
        return thingNameRu;
    }

    public void setThingNameRu(String thingNameRu) {
        this.thingNameRu = thingNameRu;
    }
}
