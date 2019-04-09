package by.neon.travelassistant.config.sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

import by.neon.travelassistant.config.sqlite.model.BaseEntity;
import by.neon.travelassistant.config.sqlite.model.City;
import by.neon.travelassistant.config.sqlite.model.Country;

@Dao
public interface BaseDao<T extends BaseEntity> {
    @Insert
    long insert(T entity);

    @Insert
    List<Long> insert(T[] entities);

    @Update
    int update(T entity);

    @Delete
    int delete(T entity);
}
