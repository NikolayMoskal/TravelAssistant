package by.neon.travelassistant.config.sqlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import by.neon.travelassistant.config.sqlite.DbConstants;

@Entity(tableName = DbConstants.TABLE_AIRPORTS,
        indices = @Index(name = DbConstants.AIRPORTS_CITIES_FK_NAME, value = DbConstants.AIRPORTS_CITIES_FK_COLUMN),
        foreignKeys = @ForeignKey(entity = CityDb.class, parentColumns = DbConstants.ID, childColumns = DbConstants.AIRPORTS_CITIES_FK_COLUMN, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class AirportDb extends BaseDbEntity {
    @ColumnInfo(name = DbConstants.AIRPORTS_COLUMN_AIRPORT_NAME)
    private String name;
    @ColumnInfo(name = DbConstants.AIRPORTS_COLUMN_LOCATION)
    private String location;
    @ColumnInfo(name = DbConstants.AIRPORTS_COLUMN_IATA_CODE)
    private String iataCode;
    @ColumnInfo(name = DbConstants.AIRPORTS_COLUMN_ICAO_CODE)
    private String icaoCode;
    @ColumnInfo(name = DbConstants.AIRPORTS_CITIES_FK_COLUMN)
    private long cityId;
    @Ignore
    private CityDb cityDb;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public CityDb getCityDb() {
        return cityDb;
    }

    public void setCityDb(CityDb cityDb) {
        this.cityDb = cityDb;
    }
}
