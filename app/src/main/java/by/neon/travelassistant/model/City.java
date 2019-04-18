package by.neon.travelassistant.model;

public class City extends Entity {
    private String cityName;
    private String cityCode;
    private long countryId;
    private Country country;

    public String getCityName() {
        return cityName;
    }

    public City setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCityCode() {
        return cityCode;
    }

    public City setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public long getCountryId() {
        return countryId;
    }

    public City setCountryId(long countryId) {
        this.countryId = countryId;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public City setCountry(Country country) {
        this.country = country;
        return this;
    }
}
