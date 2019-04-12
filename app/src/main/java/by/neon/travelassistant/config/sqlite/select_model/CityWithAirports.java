package by.neon.travelassistant.config.sqlite.select_model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;
import by.neon.travelassistant.config.sqlite.model.Airport;
import by.neon.travelassistant.config.sqlite.model.City;

public class CityWithAirports {
    @Embedded
    private City city;
    @Relation(entityColumn = DbConstants.AIRPORTS_CITIES_FK_COLUMN, parentColumn = DbConstants.ID)
    private List<Airport> airports;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
