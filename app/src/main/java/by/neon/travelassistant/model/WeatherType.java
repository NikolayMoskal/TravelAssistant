package by.neon.travelassistant.model;

/**
 * The weather type model.
 */
public class WeatherType extends Entity {
    /**
     * The type of weather.
     */
    private String type;

    /**
     * Gets the type of weather.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of weather.
     *
     * @param type the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
