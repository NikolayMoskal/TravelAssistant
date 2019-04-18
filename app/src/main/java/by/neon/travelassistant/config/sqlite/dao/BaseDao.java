package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.BaseDbEntity;

// TODO Room doesn't supports correctly rewrite INSERT query with @Query annotation
@Dao
public abstract class BaseDao<T extends BaseDbEntity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(T[] entities);
}
