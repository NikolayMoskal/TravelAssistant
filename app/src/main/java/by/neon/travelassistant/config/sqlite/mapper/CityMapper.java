package by.neon.travelassistant.config.sqlite.mapper;

import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.model.City;

public class CityMapper extends BaseMapper<City, CityDb> {
    @Override
    public CityDb from(City source) {
        CountryMapper mapper = new CountryMapper();
        CityDb cityDb = new CityDb();
        cityDb.setId(source.getId());
        cityDb.setCityName(source.getCityName());
        cityDb.setCityCode(source.getCityCode());
        cityDb.setCountryId(source.getCountryId());
        cityDb.setCountryDb(mapper.from(source.getCountry()));
        return cityDb;
    }

    @Override
    public City to(CityDb source) {
        CountryMapper mapper = new CountryMapper();
        City city = new City();
        city.setId(source.getId());
        city.setCityCode(source.getCityCode());
        city.setCityName(source.getCityName());
        city.setCountryId(source.getCountryId());
        city.setCountry(mapper.to(source.getCountryDb()));
        return city;
    }
}
