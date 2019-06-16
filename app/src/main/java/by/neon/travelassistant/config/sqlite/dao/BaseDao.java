package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.BaseDbEntity;

/**
 * The DAO class that contains the basic operations for {@link BaseDbEntity} model.
 *
 * @param <T> the type of model. Must be a derived type of {@link BaseDbEntity}.
 */
@Dao
public abstract class BaseDao<T extends BaseDbEntity> {
    /**
     * Inserts the entity in mapped table.
     *
     * @param entity the entity to insert.
     * @return the primary key of inserted record.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T entity);

    /**
     * Inserts the array of entities in mapped table. Must have at least 1 entity to insert.
     *
     * @param entities the array of entities to insert.
     * @return the list of primary keys of inserted records.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(T[] entities);

    /**
     * Updates the entity in mapped table.
     *
     * @param entity the replacement entity with existing primary key.
     * @return the number 1 if record was updated or 0 if not.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(T entity);

    /**
     * Updates the many records with given replacements. Each entity must have an existing primary key.
     * Must have at least 1 entity to update.
     *
     * @param entities the list of replacements.
     * @return the count of updated entities.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(List<T> entities);
}
