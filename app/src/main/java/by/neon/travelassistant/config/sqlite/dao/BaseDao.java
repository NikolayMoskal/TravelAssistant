package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.BaseEntity;

// TODO Room doesn't supports correctly rewrite INSERT query with @Query annotation
@Dao
public interface BaseDao<T extends BaseEntity> {
    @Insert
    long insert(T entity);

    @Insert
    List<Long> insert(T[] entities);
}
