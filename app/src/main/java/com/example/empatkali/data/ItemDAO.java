package com.example.empatkali.data;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDao(Item item);

    @Delete
    void deleteDao(Item item);

    @Query("SELECT * FROM Item")
    LiveData<Item> getItemsDao();
}
