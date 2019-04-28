package by.neon.travelassistant.model;

import android.location.Location;

public class OwmCity {
    private long owmId;
    private String name;
    private String countryCode;
    private Location location;

    public long getOwmId() {
        return owmId;
    }

    public void setOwmId(long owmId) {
        this.owmId = owmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
