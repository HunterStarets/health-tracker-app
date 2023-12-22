package com.example.health_tracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entries")
    List<Entry> getAll();

    @Query("SELECT * FROM entries WHERE id = :id LIMIT 1")
    Entry findById(long id);

    @Update
    void update(Entry entry);

    @Insert
    void insertAll(Entry... entries);

    @Delete
    void delete(Entry entry);
}

