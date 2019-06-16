package by.neon.travelassistant.model;

import java.util.Locale;

/**
 * The gender model.
 */
public class Gender extends Entity {
    /**
     * The gender type in en-US locale.
     */
    private String genderEn;
    /**
     * The gender type in ru-RU locale.
     */
    private String genderRu;

    /**
     * Gets the gender type in en-US locale.
     * @return the gender type.
     */
    public String getGenderEn() {
        return genderEn;
    }

    /**
     * Sets the gender type in en-US locale.
     * @param genderEn the gender to set.
     */
    public void setGenderEn(String genderEn) {
        this.genderEn = genderEn;
    }

    /**
     * Gets the gender type in ru-RU locale.
     * @return the gender type.
     */
    public String getGenderRu() {
        return genderRu;
    }

    /**
     * Sets the gender type in ru-RU locale.
     * @param genderRu the gender to set.
     */
    public void setGenderRu(String genderRu) {
        this.genderRu = genderRu;
    }

    /**
     * Gets the gender type based on current locale. If current locale is not defined in application
     * then returns in en-US locale.
     * @return the gender type.
     */
    public String getGender() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? genderRu : genderEn;
    }
}
