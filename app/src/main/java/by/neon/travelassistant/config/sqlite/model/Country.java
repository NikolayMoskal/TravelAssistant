package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import by.neon.travelassistant.config.sqlite.DbConstants;

@Entity(tableName = DbConstants.TABLE_COUNTRIES)
public class Country extends BaseEntity {
    @ColumnInfo(name = DbConstants.COUNTRIES_COLUMN_COUNTRY_NAME)
    private String countryName;
    @ColumnInfo(name = DbConstants.COUNTRIES_COLUMN_COUNTRY_CODE)
    private String countryCode;

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
}
