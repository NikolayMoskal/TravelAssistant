package by.neon.travelassistant.model;

public class Country extends Entity {
    private String countryName;
    private String countryCode;

    public String getCountryName() {
        return countryName;
    }

    public Country setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }
}
