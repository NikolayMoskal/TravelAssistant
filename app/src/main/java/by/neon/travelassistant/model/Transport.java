package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class Transport extends Entity {
    @JsonProperty("name_en-us")
    private String nameEn;
    @JsonProperty("name_ru-ru")
    private String nameRu;
    @JsonProperty("handLuggageWeight")
    private double handPackWeight;
    @JsonProperty("maxWeight")
    private double maxWeight;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public double getHandPackWeight() {
        return handPackWeight;
    }

    public void setHandPackWeight(double handPackWeight) {
        this.handPackWeight = handPackWeight;
    }

    public String getName() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? nameRu : nameEn;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
