package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.List;

import by.neon.travelassistant.constant.DbConstants;

@Entity(tableName = DbConstants.TABLE_THINGS,
        indices = {
                @Index(name = DbConstants.THINGS_TYPES_FK_NAME, value = DbConstants.THINGS_TYPES_FK_COLUMN),
                @Index(name = DbConstants.THINGS_GENDERS_FK_NAME, value = DbConstants.THINGS_GENDERS_FK_COLUMN)
        },
        foreignKeys = {
                @ForeignKey(entity = TypeDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_TYPES_FK_COLUMN,
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = GenderDb.class,
                        parentColumns = DbConstants.ID,
                        childColumns = DbConstants.THINGS_GENDERS_FK_COLUMN,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        })
public class ThingDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_EN_US)
    private String thingNameEn;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_RU_RU)
    private String thingNameRu;
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_WEIGHT)
    private double weight;
    @ColumnInfo(name = DbConstants.THINGS_TYPES_FK_COLUMN)
    private long typeId;
    @Ignore
    private TypeDb typeDb;
    @Ignore
    private List<CategoryDb> categoryDbs;
    @ColumnInfo(name = DbConstants.THINGS_GENDERS_FK_COLUMN)
    private long genderId;
    @Ignore
    private GenderDb genderDb;
    @Ignore
    private List<WeatherTypeDb> weatherTypeDbs;


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

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public TypeDb getTypeDb() {
        return typeDb;
    }

    public void setTypeDb(TypeDb typeDb) {
        this.typeDb = typeDb;
    }

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    public GenderDb getGenderDb() {
        return genderDb;
    }

    public void setGenderDb(GenderDb genderDb) {
        this.genderDb = genderDb;
    }

    public List<WeatherTypeDb> getWeatherTypeDbs() {
        return weatherTypeDbs;
    }

    public void setWeatherTypeDbs(List<WeatherTypeDb> weatherTypeDbs) {
        this.weatherTypeDbs = weatherTypeDbs;
    }

    public List<CategoryDb> getCategoryDbs() {
        return categoryDbs;
    }

    public void setCategoryDbs(List<CategoryDb> categoryDbs) {
        this.categoryDbs = categoryDbs;
    }
}
