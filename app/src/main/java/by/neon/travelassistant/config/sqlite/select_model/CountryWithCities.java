package by.neon.travelassistant.config.sqlite.select_model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.City;
import by.neon.travelassistant.config.sqlite.model.Country;

public class CountryWithCities {
    @Embedded
    private Country country;
    @Relation(entityColumn = DbConstants.CITIES_COUNTRIES_FK_COLUMN, parentColumn = DbConstants.ID, entity = City.class)
    private List<CityWithAirports> cities;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<CityWithAirports> getCities() {
        return cities;
    }

    public void setCities(List<CityWithAirports> cities) {
        this.cities = cities;
    }
}
