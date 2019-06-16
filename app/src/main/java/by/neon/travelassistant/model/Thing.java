package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Locale;

/**
 * The thing model.
 */
public class Thing extends Entity {
    /**
     * The thing name in en-US locale.
     */
    @JsonProperty("thingName_en-US")
    private String thingNameEn;
    /**
     * The thing name in ru-RU locale.
     */
    @JsonProperty("thingName_ru-RU")
    private String thingNameRu;
    /**
     * The weight of thing.
     */
    private double weight;
    /**
     * The type of this thing. Each thing can be related with one of many types - the clothes,
     * the shoes, the documents etc.
     */
    private String type;
    /**
     * The list of related thing categories. Each thing can be related with one or more categories -
     * it can be taken for restaurant, for business trip, for travel etc.
     */
    private List<String> category;
    /**
     * The gender type. The thing can be used by men, by women or it can be universal.
     */
    private String gender;
    /**
     * The list of weather types related with thing. The thing can be taken for one or more weather
     * types - when it rains, when the sun shines etc.
     */
    private List<String> weatherType;
    /**
     * The ID of thing type.
     *
     * @see #type
     */
    @JsonIgnore
    private long typeId;
    /**
     * The ID of gender.
     *
     * @see #gender
     */
    @JsonIgnore
    private long genderId;

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
     * Gets the thing type.
     *
     * @return the thing type.
     * @see #type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the thing type.
     *
     * @param type the thing type to set.
     * @see #type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the list of thing categories.
     *
     * @return the list of categories.
     * @see #category
     */
    public List<String> getCategory() {
        return category;
    }

    /**
     * Sets the list of thing categories.
     *
     * @param category the list of categories to set.
     * @see #category
     */
    public void setCategory(List<String> category) {
        this.category = category;
    }

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
     * Gets the thing name in current locale. If current locale is not defined in application
     * then returns in en-US locale.
     *
     * @return the thing name.
     */
    public String getThingName() {
        switch (Locale.getDefault().getLanguage()) {
            case "ru":
                return thingNameRu;
            case "en":
                return thingNameEn;
            default:
                return thingNameEn;
        }
    }

    /**
     * Gets the gender instance.
     *
     * @return the gender instance.
     * @see #gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender instance.
     *
     * @param gender the gender to set.
     * @see #gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the list of weather types.
     *
     * @return the list of weather types.
     * @see #weatherType
     */
    public List<String> getWeatherType() {
        return weatherType;
    }

    /**
     * Sets the list of weather types.
     *
     * @param weatherType the list to set.
     * @see #weatherType
     */
    public void setWeatherType(List<String> weatherType) {
        this.weatherType = weatherType;
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
}
