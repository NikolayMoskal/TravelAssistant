package by.neon.travelassistant.model;

import java.util.Locale;

public class Gender extends Entity {
    private String genderEn;
    private String genderRu;

    public String getGenderEn() {
        return genderEn;
    }

    public void setGenderEn(String genderEn) {
        this.genderEn = genderEn;
    }

    public String getGenderRu() {
        return genderRu;
    }

    public void setGenderRu(String genderRu) {
        this.genderRu = genderRu;
    }

    public String getGender() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? genderRu : genderEn;
    }
}
