package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.BaseDbEntity;

// TODO Room doesn't supports correctly rewrite INSERT query with @Query annotation
@Dao
public abstract class BaseDao<T extends BaseDbEntity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(T[] entities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(T entity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(List<T> entities);
}
