package com.yeck.classsqlite1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE phoneNumber=:phoneNumber")
    User getUser(String phoneNumber);

    @Query("SELECT * FROM user WHERE name=:name")
    User getUser2(String name);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
