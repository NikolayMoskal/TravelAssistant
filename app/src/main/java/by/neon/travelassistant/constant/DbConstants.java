package by.neon.travelassistant.constant;

/**
 * Contains all database table names and its columns and indices.
 */
public final class DbConstants {
    /**
     * The name of database.
     */
    public static final String DATABASE = "TravelDb";
    /**
     * The primary key column in each table.
     */
    public static final String ID = "_id";

    /**
     * The name of things table.
     */
    public static final String TABLE_THINGS = "THINGS";
    /**
     * The name of thing name column in en-US locale.
     */
    public static final String THINGS_COLUMN_THING_NAME_EN_US = "THING_NAME_EN_US";
    /**
     * The name of thing name column in ru-RU locale.
     */
    public static final String THINGS_COLUMN_THING_NAME_RU_RU = "THING_NAME_RU_RU";
    /**
     * The name of thing weight column.
     */
    public static final String THINGS_COLUMN_WEIGHT = "WEIGHT";
    /**
     * The name of foreign key column to thing types table.
     */
    public static final String THINGS_TYPES_FK_COLUMN = "TYPE_ID";
    /**
     * The name of foreign key index to thing types table.
     */
    public static final String THINGS_TYPES_FK_NAME = "THINGS_TYPES_FK";
    /**
     * The name of foreign key column to genders table.
     */
    public static final String THINGS_GENDERS_FK_COLUMN = "GENDER_ID";
    /**
     * The name of foreign key index to genders table.
     */
    public static final String THINGS_GENDERS_FK_NAME = "THINGS_GENDERS_FK";

    /**
     * The name of thing types table.
     */
    public static final String TABLE_TYPES = "TYPES";
    /**
     * The name of type in en-US locale.
     */
    public static final String TYPES_COLUMN_TYPE_NAME_EN_US = "TYPE_NAME_EN_US";
    /**
     * The name of type in ru-RU locale.
     */
    public static final String TYPES_COLUMN_TYPE_NAME_RU_RU = "TYPE_NAME_RU_RU";

    /**
     * The name of thing categories table.
     */
    public static final String TABLE_CATEGORIES = "CATEGORIES";
    /**
     * The name of category in en-US locale.
     */
    public static final String CATEGORIES_COLUMN_CATEGORY_NAME_EN_US = "CATEGORY_NAME_EN_US";
    /**
     * The name of category in ru-RU locale.
     */
    public static final String CATEGORIES_COLUMN_CATEGORY_NAME_RU_RU = "CATEGORY_NAME_RU_RU";

    /**
     * The name of genders table.
     */
    public static final String TABLE_GENDERS = "GENDERS";
    /**
     * The name of gender type in en-US locale.
     */
    public static final String GENDERS_COLUMN_GENDER_TYPE_EN_US = "GENDER_TYPE_EN_US";
    /**
     * The name of gender type in ru-RU locale.
     */
    public static final String GENDERS_COLUMN_GENDER_TYPE_RU_RU = "GENDER_TYPE_RU_RU";

    /**
     * The name of weather types table.
     */
    public static final String TABLE_WEATHER_TYPES = "WEATHER_TYPES";
    /**
     * The name of weather type name column.
     */
    public static final String WEATHER_TYPES_COLUMN_TYPE_NAME = "TYPE_NAME";

    /**
     * The name of transport types table.
     */
    public static final String TABLE_TRANSPORTS = "TRANSPORTS";
    /**
     * The name of transport type in en-US locale.
     */
    public static final String TRANSPORTS_COLUMN_TRANSPORT_NAME_EN_US = "TRANSPORT_NAME_EN_US";
    /**
     * The name of transport type in ru-RU locale.
     */
    public static final String TRANSPORTS_COLUMN_TRANSPORT_NAME_RU_RU = "TRANSPORT_NAME_RU_RU";
    /**
     * The name of column of hand luggage weight.
     */
    public static final String TRANSPORTS_COLUMN_HAND_PACK_WEIGHT = "HAND_PACK_WEIGHT";
    /**
     * The name of column of maximum luggage weight.
     */
    public static final String TRANSPORTS_COLUMN_MAX_PACK_WEIGHT = "MAX_WEIGHT";

    /**
     * The name of auxiliary table between things and categories tables.
     */
    public static final String TABLE_THINGS_CATEGORIES = "THINGS_CATEGORIES";
    /**
     * The name of foreign key column to things table.
     */
    public static final String THINGS_CATEGORIES_COLUMN_THING_ID = "THING_ID";
    /**
     * The name of foreign key column to categories table.
     */
    public static final String THINGS_CATEGORIES_COLUMN_CATEGORY_ID = "CATEGORY_ID";

    /**
     * The name of auxiliary table between things and weather types tables.
     */
    public static final String TABLE_THINGS_WEATHER_TYPES = "THINGS_WEATHER_TYPES";
    /**
     * The name of foreign key to things table.
     */
    public static final String THINGS_WEATHER_TYPES_COLUMN_THING_ID = "THING_ID";
    /**
     * The name of foreign key to weather types table.
     */
    public static final String THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID = "WEATHER_TYPE_ID";
}
