package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;

@Entity(tableName = DbConstants.TABLE_COUNTRIES,
        indices = @Index(value = DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME, name = DbConstants.COUNTRIES_INDEX_UNIQUE_NAME, unique = true))
public class CountryDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME)
    private String countryName;
    @ColumnInfo(name = DbConstants.COUNTRIES_COLUMN_COUNTRY_CODE)
    private String countryCode;
    @Ignore
    private List<CityDb> cities;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<CityDb> getCities() {
        return cities;
    }

    @SuppressWarnings("unused")
    public List<CityDb> addCity(CityDb cityDb) {
        if (cities == null) {
            cities = new ArrayList<>(0);
        }
        cityDb.setCountryId(getId());
        cities.add(cityDb);
        return cities;
    }

    public void setCities(List<CityDb> cities) {
        for (CityDb cityDb : cities) {
            cityDb.setCountryId(getId());
        }
        this.cities = cities;
    }
}
