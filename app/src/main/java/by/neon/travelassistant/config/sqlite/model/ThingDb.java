package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.List;

import by.neon.travelassistant.constant.DbConstants;
import by.neon.travelassistant.model.Thing;

/**
 * The database entity for thing.
 */
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
    /**
     * The thing name in en-US locale.
     */
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_EN_US)
    private String thingNameEn;
    /**
     * The thing name in ru-RU locale.
     */
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_THING_NAME_RU_RU)
    private String thingNameRu;
    /**
     * The weight of thing.
     */
    @ColumnInfo(name = DbConstants.THINGS_COLUMN_WEIGHT)
    private double weight;
    /**
     * The ID of thing type.
     * @see #typeDb
     */
    @ColumnInfo(name = DbConstants.THINGS_TYPES_FK_COLUMN)
    private long typeId;
    /**
     * The type of this thing. Each thing can be related with one of many types - the clothes,
     * the shoes, the documents etc.
     */
    @Ignore
    private TypeDb typeDb;
    /**
     * The list of related thing categories. Each thing can be related with one or more categories -
     * it can be taken for restaurant, for business trip, for travel etc.
     */
    @Ignore
    private List<CategoryDb> categoryDbs;
    /**
     * The ID of gender.
     * @see #genderDb
     */
    @ColumnInfo(name = DbConstants.THINGS_GENDERS_FK_COLUMN)
    private long genderId;
    /**
     * The gender instance. The thing can be only for men, for women or it can be universal.
     */
    @Ignore
    private GenderDb genderDb;
    /**
     * The list of weather types related with thing. The thing can be taken for one or more weather
     * types - when it rains, when the sun shines etc.
     */
    @Ignore
    private List<WeatherTypeDb> weatherTypeDbs;

    /**
     * Gets the weight of thing.
     *
     * @return the weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of thing.
     *
     * @param weight the weight to set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the name of thing in en-US locale.
     *
     * @return the name of thing.
     */
    public String getThingNameEn() {
        return thingNameEn;
    }

    /**
     * Sets the name of thing in en-US locale.
     *
     * @param thingNameEn the name to set.
     */
    public void setThingNameEn(String thingNameEn) {
        this.thingNameEn = thingNameEn;
    }

    /**
     * Gets the name of thing in ru-RU locale.
     *
     * @return the name of thing.
     */
    public String getThingNameRu() {
        return thingNameRu;
    }

    /**
     * Sets the name of thing in ru-RU locale.
     *
     * @param thingNameRu the name to set.
     */
    public void setThingNameRu(String thingNameRu) {
        this.thingNameRu = thingNameRu;
    }

    /**
     * Gets the ID of thing type.
     *
     * @return the ID.
     */
    public long getTypeId() {
        return typeId;
    }

    /**
     * Sets the ID of thing type.
     *
     * @param typeId the ID to set.
     */
    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    /**
     * Gets the thing type instance.
     *
     * @return the thing type.
     * @see #typeDb
     */
    public TypeDb getTypeDb() {
        return typeDb;
    }

    /**
     * Sets the thing type instance.
     *
     * @param typeDb the thing type to set.
     * @see #typeDb
     */
    public void setTypeDb(TypeDb typeDb) {
        this.typeDb = typeDb;
    }

    /**
     * Gets the ID of gender.
     *
     * @return the gender ID.
     */
    public long getGenderId() {
        return genderId;
    }

    /**
     * Sets the gender ID.
     *
     * @param genderId the ID to set.
     */
    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    /**
     * Gets the gender instance.
     *
     * @return the gender instance.
     * @see #genderDb
     */
    public GenderDb getGenderDb() {
        return genderDb;
    }

    /**
     * Sets the gender instance.
     *
     * @param genderDb the gender to set.
     * @see #genderDb
     */
    public void setGenderDb(GenderDb genderDb) {
        this.genderDb = genderDb;
    }

    /**
     * Gets the list of weather types.
     *
     * @return the list of weather types.
     * @see #weatherTypeDbs
     */
    public List<WeatherTypeDb> getWeatherTypeDbs() {
        return weatherTypeDbs;
    }

    /**
     * Sets the list of weather types.
     *
     * @param weatherTypeDbs the list to set.
     * @see #weatherTypeDbs
     */
    public void setWeatherTypeDbs(List<WeatherTypeDb> weatherTypeDbs) {
        this.weatherTypeDbs = weatherTypeDbs;
    }

    /**
     * Gets the list of thing categories.
     *
     * @return the list of categories.
     * @see #categoryDbs
     */
    public List<CategoryDb> getCategoryDbs() {
        return categoryDbs;
    }

    /**
     * Sets the list of thing categories.
     *
     * @param categoryDbs the list of categories to set.
     * @see #categoryDbs
     */
    public void setCategoryDbs(List<CategoryDb> categoryDbs) {
        this.categoryDbs = categoryDbs;
    }
}
