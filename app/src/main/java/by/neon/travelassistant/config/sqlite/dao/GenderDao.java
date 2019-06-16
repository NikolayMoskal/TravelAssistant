package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO class for {@link GenderDb} model.
 */
@Dao
public abstract class GenderDao extends BaseDao<GenderDb> {
    /**
     * Gets all genders.
     *
     * @return the list of genders.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS)
    public abstract List<GenderDb> getAll();

    /**
     * Gets the gender by its type.
     *
     * @param type the type of gender.
     * @return the gender or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS + " WHERE " + DbConstants.GENDERS_COLUMN_GENDER_TYPE_EN_US + " = :type LIMIT 1")
    public abstract GenderDb getByType(String type);

    /**
     * Gets the genders by their types.
     *
     * @param types the list of types.
     * @return the list of genders or empty list if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS + " WHERE " + DbConstants.GENDERS_COLUMN_GENDER_TYPE_EN_US + " IN (:types)")
    public abstract List<GenderDb> getByTypes(List<String> types);

    /**
     * Gets the single gender by its ID.
     *
     * @param id the ID of the gender record in database.
     * @return the gender or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_GENDERS + " WHERE " + DbConstants.ID + " = :id")
    public abstract GenderDb getById(long id);
}
