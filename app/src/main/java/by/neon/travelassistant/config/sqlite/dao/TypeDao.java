package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.constant.DbConstants;

/**
 * The DAO class for {@link TypeDb} model.
 */
@Dao
public abstract class TypeDao extends BaseDao<TypeDb> {
    /**
     * Gets all thing types.
     *
     * @return the list of thing types.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES)
    public abstract List<TypeDb> getAll();

    /**
     * Gets the thing type by its ID.
     *
     * @param id the ID of the thing type record in database.
     * @return the thing type or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES + " WHERE " + DbConstants.ID + " = :id")
    public abstract TypeDb getById(long id);

    /**
     * Gets the thing type record by given name.
     *
     * @param name the name of thing type.
     * @return the thing type or NULL if not found.
     */
    @Query("SELECT * FROM " + DbConstants.TABLE_TYPES + " WHERE " + DbConstants.TYPES_COLUMN_TYPE_NAME_EN_US + " = :name LIMIT 1")
    public abstract TypeDb getByName(String name);
}
