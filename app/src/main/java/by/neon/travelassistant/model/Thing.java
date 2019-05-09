package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Locale;

public class Thing extends Entity {
    @JsonProperty("thingName_en-US")
    private String thingNameEn;
    @JsonProperty("thingName_ru-RU")
    private String thingNameRu;
    private double weight;
    private String type;
    private List<String> category;
    private String gender;
    private List<String> weatherType;
    @JsonIgnore
    private long typeId;
    @JsonIgnore
    private long genderId;

    public String getThingNameEn() {
        return thingNameEn;
    }

    public void setThingNameEn(String thingNameEn) {
        this.thingNameEn = thingNameEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getThingNameRu() {
        return thingNameRu;
    }

    public void setThingNameRu(String thingNameRu) {
        this.thingNameRu = thingNameRu;
    }

    public String getThingName() {
        switch (Locale.getDefault().getLanguage()) {
            case "ru": return thingNameRu;
            case "en": return thingNameEn;
            default: return thingNameEn;
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(List<String> weatherType) {
        this.weatherType = weatherType;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }
}
