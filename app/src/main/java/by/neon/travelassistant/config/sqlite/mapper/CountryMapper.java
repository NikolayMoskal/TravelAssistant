package by.neon.travelassistant.config.sqlite.mapper;

import by.neon.travelassistant.model.Country;
import by.neon.travelassistant.config.sqlite.model.CountryDb;

public class CountryMapper extends BaseMapper<Country, CountryDb> {
    @Override
    public CountryDb from(Country source) {
        CountryDb countryDb = new CountryDb();
        countryDb.setId(source.getId());
        countryDb.setCountryName(source.getCountryName());
        countryDb.setCountryCode(source.getCountryCode());
        return countryDb;
    }

    @Override
    public Country to(CountryDb source) {
        Country country = new Country();
        country.setId(source.getId());
        country.setCountryCode(source.getCountryCode());
        country.setCountryName(source.getCountryName());
        return country;
    }
}
