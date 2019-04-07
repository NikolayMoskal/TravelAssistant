package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.config.sqlite.DbConstants;

@Entity(tableName = DbConstants.TABLE_CITIES,
        indices = @Index(name = DbConstants.CITIES_COUNTRIES_FK_NAME, value = DbConstants.CITIES_COUNTRIES_FK_COLUMN),
        foreignKeys = @ForeignKey(entity = Country.class, parentColumns = DbConstants.ID, childColumns = DbConstants.CITIES_COUNTRIES_FK_COLUMN, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class City extends BaseEntity {
    @ColumnInfo(name = DbConstants.CITIES_COLUMN_CITY_NAME)
    private String cityName;
    @ColumnInfo(name = DbConstants.CITIES_COLUMN_CITY_CODE)
    private String cityCode;
    @ColumnInfo(name = DbConstants.CITIES_COUNTRIES_FK_COLUMN)
    private long countryId;

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
}
