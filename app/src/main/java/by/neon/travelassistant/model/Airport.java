package by.neon.travelassistant.model;

import android.location.Location;

public class Airport extends Entity {
    private String airportName;
    private Location location;
    private String iataCode;
    private String icaoCode;
    private long cityId;
    private City city;

    public String getAirportName() {
        return airportName;
    }

    public Airport setAirportName(String airportName) {
        this.airportName = airportName;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Airport setLocation(Location location) {
        this.location = location;
        return this;
    }

    public String getIataCode() {
        return iataCode;
    }

    public Airport setIataCode(String iataCode) {
        this.iataCode = iataCode;
        return this;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public Airport setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
        return this;
    }

    public long getCityId() {
        return cityId;
    }

    public Airport setCityId(long cityId) {
        this.cityId = cityId;
        return this;
    }

    public City getCity() {
        return city;
    }

    public Airport setCity(City city) {
        this.city = city;
        return this;
    }
}
