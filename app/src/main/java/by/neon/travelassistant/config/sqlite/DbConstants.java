package by.neon.travelassistant.config.sqlite;

public final class DbConstants {
    public static final String DATABASE = "TravelDb";
    public static final String ID = "_id";

    public static final String TABLE_AIRPORTS = "AIRPORTS";
    public static final String AIRPORTS_COLUMN_AIRPORT_NAME = "AIRPORT_NAME";
    public static final String AIRPORTS_COLUMN_IATA_CODE = "IATA_CODE";
    public static final String AIRPORTS_COLUMN_ICAO_CODE = "ICAO_CODE";
    public static final String AIRPORTS_COLUMN_LOCATION = "LOCATION";
    public static final String AIRPORTS_CITIES_FK_COLUMN = "CITY_ID";
    public static final String AIRPORTS_CITIES_FK_NAME = "AIRPORTS_CITIES_FK";

    public static final String TABLE_COUNTRIES = "COUNTRIES";
    public static final String COUNTRIES_COLUMN_COUNTRY_NAME = "COUNTRY_NAME";
    public static final String COUNTRIES_COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
    public static final String COUNTRIES_INDEX_UNIQUE_NAME = "COUNTRIES_NAME_UNIQUE";

    public static final String TABLE_CITIES = "CITIES";
    public static final String CITIES_COLUMN_CITY_NAME = "CITY_NAME";
    public static final String CITIES_COLUMN_CITY_CODE = "CITY_CODE";
    public static final String CITIES_COUNTRIES_FK_COLUMN = "COUNTRY_ID";
    public static final String CITIES_COUNTRIES_FK_NAME = "CITIES_COUNTRIES_FK";
}
