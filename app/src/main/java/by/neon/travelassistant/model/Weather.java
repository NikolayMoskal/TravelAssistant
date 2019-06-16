package by.neon.travelassistant.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The weather model.
 */
public class Weather {
    /**
     * The list of weather {@link State}.
     */
    private List<State> states;
    /**
     * The current temperature.
     */
    private double currentTemp;
    /**
     * The minimum temperature for selected period.
     */
    private double minTemp;
    /**
     * The maximum temperature for selected period.
     */
    private double maxTemp;
    /**
     * The amount of rainfall.
     */
    private double rainVolume;
    /**
     * The amount of snowfall.
     */
    private double snowVolume;
    /**
     * The date of this weather data snapshot.
     */
    private Date calculationDate;
    /**
     * The name of the city or region for this weather data.
     */
    private String cityName;
    /**
     * The country code given in ISO 1366-1 format.
     */
    private String countryCode;
    /**
     * The WGS84-based geo spatial location of this city or region.
     */
    private Location location;
    /**
     * The unique 6-digital city or region code in OpenWeatherMap region database.
     */
    private long cityId;

    /**
     * Gets the list of weather states.
     *
     * @return the states of weather.
     * @see State
     */
    public List<State> getStates() {
        return states;
    }

    /**
     * Sets the list of weather states.
     *
     * @param states the states to set.
     * @see State
     */
    public void setStates(List<State> states) {
        this.states = states;
    }

    /**
     * Gets the current temperature
     *
     * @return the temperature value.
     */
    public double getCurrentTemp() {
        return currentTemp;
    }

    /**
     * Sets the current temperature.
     *
     * @param currentTemp the temperature to set.
     */
    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    /**
     * Gets the minimum temperature.
     *
     * @return the temperature.
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * Sets the minimum temperature.
     *
     * @param minTemp the temperature to set.
     */
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    /**
     * Gets the maximum temperature.
     *
     * @return the temperature.
     */
    public double getMaxTemp() {
        return maxTemp;
    }

    /**
     * Sets the maximum temperature.
     *
     * @param maxTemp the temperature to set.
     */
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    /**
     * Gets the rain volume.
     *
     * @return the rain volume.
     */
    public double getRainVolume() {
        return rainVolume;
    }

    /**
     * Sets the rain volume.
     *
     * @param rainVolume the rain volume to set.
     */
    public void setRainVolume(double rainVolume) {
        this.rainVolume = rainVolume;
    }

    /**
     * Gets the snow volume.
     *
     * @return the snow volume.
     */
    public double getSnowVolume() {
        return snowVolume;
    }

    /**
     * Sets the snow volume.
     *
     * @param snowVolume the snoe volume to set.
     */
    public void setSnowVolume(double snowVolume) {
        this.snowVolume = snowVolume;
    }

    /**
     * Gets the date of this weather data snapshot.
     *
     * @return the date.
     */
    public Date getCalculationDate() {
        return calculationDate;
    }

    /**
     * Sets the date of this weather data snapshot.
     *
     * @param calculationDate the date to set.
     */
    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    /**
     * Gets the name of the city or region for this weather data.
     *
     * @return the region name.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the name of the city or region for this weather data.
     *
     * @param cityName the region name to set.
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Gets the country code.
     *
     * @return the country code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the country code.
     *
     * @param countryCode the country code to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the region location.
     *
     * @return the location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the region location.
     *
     * @param location the location to set.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the unique city code.
     *
     * @return the city ID.
     */
    public long getCityId() {
        return cityId;
    }

    /**
     * Sets the unique city code.
     *
     * @param cityId the city ID to set.
     */
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    /**
     * Gets the one or more weather types based on this weather data.
     *
     * @return the list of weather types.
     */
    public List<String> getWeatherType() {
        List<String> types = new ArrayList<>(0);
        types.add("any");
        if (rainVolume > 0) {
            types.add("rain");
        }
        if (maxTemp < 0) {
            types.add("cold");
        }
        if (minTemp >= 0 && maxTemp <= 15) {
            types.add("normal");
        }
        if (minTemp > 15 && maxTemp <= 25) {
            types.add("warm");
        }
        if (minTemp > 25) {
            types.add("hot");
        }
        return types;
    }

    /**
     * The weather state, e.g. snow, rain, sun shines etc.
     */
    public static class State {
        /**
         * The description of this state.
         */
        private String title;
        /**
         * The unique icon URL for this state.
         */
        private String iconUrl;

        /**
         * Gets the state description.
         *
         * @return the description.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the state description.
         *
         * @param title the description to set.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gets the icon URL.
         *
         * @return the URL.
         */
        public String getIconUrl() {
            return iconUrl;
        }

        /**
         * Sets the icon URL.
         *
         * @param iconUrl the icon URL to set.
         */
        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
