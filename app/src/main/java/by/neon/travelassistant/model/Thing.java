package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Thing extends Entity {
    @JsonProperty("thingName_en-US")
    private String thingNameEn;
    @JsonProperty("thingName_ru-RU")
    private String thingNameRu;
    private double weight;
    private String type;
    private String category;

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

    public String getThingNameRu() {
        return thingNameRu;
    }

    public void setThingNameRu(String thingNameRu) {
        this.thingNameRu = thingNameRu;
    }
}
