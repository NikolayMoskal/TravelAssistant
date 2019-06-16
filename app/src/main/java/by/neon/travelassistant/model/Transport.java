package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

/**
 * The transport model.
 */
public class Transport extends Entity {
    /**
     * The name of transport in en-US locale.
     */
    @JsonProperty("name_en-us")
    private String nameEn;
    /**
     * The name of transport in ru-RU locale.
     */
    @JsonProperty("name_ru-ru")
    private String nameRu;
    /**
     * The max weight of hand luggage for this transport.
     */
    @JsonProperty("handLuggageWeight")
    private double handPackWeight;
    /**
     * The max weight of whole luggage for this transport.
     */
    @JsonProperty("maxWeight")
    private double maxWeight;

    /**
     * Gets the name of transport in en-US locale.
     *
     * @return the name.
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * Sets the name of transport in en-US locale.
     *
     * @param nameEn the name to set.
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    /**
     * Gets the name of transport in ru-RU locale.
     *
     * @return the name.
     */
    public String getNameRu() {
        return nameRu;
    }

    /**
     * Sets the name of transport in ru-RU locale.
     *
     * @param nameRu the name to set.
     */
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    /**
     * Gets the max weight of hand luggage for this transport.
     *
     * @return the weight.
     */
    public double getHandPackWeight() {
        return handPackWeight;
    }

    /**
     * Sets the max weight of hand luggage for this transport.
     *
     * @param handPackWeight the weight to set.
     */
    public void setHandPackWeight(double handPackWeight) {
        this.handPackWeight = handPackWeight;
    }

    /**
     * Gets the name in current locale. If current locale is not defined in application
     * then returns in en-US locale.
     *
     * @return the name.
     */
    public String getName() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? nameRu : nameEn;
    }

    /**
     * Gets the max weight of whole luggage for this transport.
     *
     * @return the weight.
     */
    public double getMaxWeight() {
        return maxWeight;
    }

    /**
     * Sets the max weight of whole luggage for this transport.
     *
     * @param maxWeight the weight to set.
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
