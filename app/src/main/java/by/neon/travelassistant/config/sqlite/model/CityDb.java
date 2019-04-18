package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.DbConstants;

@Entity(tableName = DbConstants.TABLE_CITIES,
        indices = @Index(name = DbConstants.CITIES_COUNTRIES_FK_NAME, value = DbConstants.CITIES_COUNTRIES_FK_COLUMN),
        foreignKeys = @ForeignKey(entity = CountryDb.class, parentColumns = DbConstants.ID, childColumns = DbConstants.CITIES_COUNTRIES_FK_COLUMN, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class CityDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.CITIES_COLUMN_CITY_NAME)
    private String cityName;
    @ColumnInfo(name = DbConstants.CITIES_COLUMN_CITY_CODE)
    private String cityCode;
    @ColumnInfo(name = DbConstants.CITIES_COUNTRIES_FK_COLUMN)
    private long countryId;
    @Ignore
    private CountryDb countryDb;
    @Ignore
    private List<AirportDb> airportDbs;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public CountryDb getCountryDb() {
        return countryDb;
    }

    public void setCountryDb(CountryDb countryDb) {
        this.countryDb = countryDb;
    }

    public List<AirportDb> getAirportDbs() {
        return airportDbs;
    }

    @SuppressWarnings("unused")
    public List<AirportDb> addAirport(AirportDb airportDb) {
        if (airportDbs == null) {
            airportDbs = new ArrayList<>(0);
        }
        airportDbs.add(airportDb);
        return airportDbs;
    }

    public void setAirportDbs(List<AirportDb> airportDbs) {
        this.airportDbs = airportDbs;
    }
}
