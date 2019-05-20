package by.neon.travelassistant.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Weather {
    private List<State> states;
    private double currentTemp;
    private double minTemp;
    private double maxTemp;
    private double rainVolume;
    private double snowVolume;
    private Date calculationDate;
    private String cityName;
    private String countryCode;
    private Location location;
    private long cityId;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(double rainVolume) {
        this.rainVolume = rainVolume;
    }

    public double getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(double snowVolume) {
        this.snowVolume = snowVolume;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

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

    public static class State {
        private String title;
        private String iconUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
