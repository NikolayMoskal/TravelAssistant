package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class Transport extends Entity {
    @JsonProperty("name_en-us")
    private String nameEn;
    @JsonProperty("name_ru-ru")
    private String nameRu;
    @JsonProperty("weight")
    private double packWeight;

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

    public double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }

    public String getName() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? nameRu : nameEn;
    }
}
