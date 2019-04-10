package by.neon.travelassistant.config;

public final class AirportInfo {
    private String iataCode;
    private String icaoCode;
    private String airportName;
    private String cityName;
    private String countryName;
    private String countryCode;
    private double latitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    AirportInfo setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    AirportInfo setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getCountryName() {
        return countryName;
    }

    AirportInfo setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    AirportInfo setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getAirportName() {
        return airportName;
    }

    AirportInfo setAirportName(String airportName) {
        this.airportName = airportName;
        return this;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    AirportInfo setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
        return this;
    }

    public String getIataCode() {
        return iataCode;
    }

    AirportInfo setIataCode(String iataCode) {
        this.iataCode = iataCode;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    AirportInfo setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }
}
