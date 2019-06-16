package by.neon.travelassistant.model;

/**
 * The thing type model.
 */
public class Type extends Entity {
    /**
     * The type in en-US locale.
     */
    private String typeEn;
    /**
     * The type in ru-RU locale.
     */
    private String typeRu;

    /**
     * Gets the type in en-US locale.
     *
     * @return the type.
     */
    public String getTypeEn() {
        return typeEn;
    }

    /**
     * Sets the type in en-US locale.
     *
     * @param typeEn the type to set.
     */
    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    /**
     * Gets the type in ru-RU locale.
     *
     * @return the type.
     */
    public String getTypeRu() {
        return typeRu;
    }

    /**
     * Sets the type in ru-RU locale.
     *
     * @param typeRu the type to set.
     */
    public void setTypeRu(String typeRu) {
        this.typeRu = typeRu;
    }
}
