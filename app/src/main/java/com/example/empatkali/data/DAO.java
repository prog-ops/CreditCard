package com.example.empatkali.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Item item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFacedata(Facedata facedata);

    @Delete
    void deleteItem(Item item);
    @Delete
    void deleteFacedata(Facedata facedata);

    @Query("SELECT * FROM Item WHERE id=:id")
    LiveData<Item> getItemDao(long id);

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getItemListDao();


    @Query("SELECT * FROM Facedata")
    LiveData<Facedata> getFacedataDao();
}

/* !
! error: Dao class must be annotated with @Dao
! public interface DAO {
! warning: Current JDK version 1.8.0_241-b07 has a bug
! (https://bugs.openjdk.java.net/browse/JDK-8007720) that
! prevents Room from being incremental.
! Consider using JDK 11+ or the embedded JDK shipped with
! Android Studio 3.5+.[WARN] Incremental annotation processing
! requested, but support is disabled because the following
! processors are not incremental: androidx.room.RoomProcessor
! (DYNAMIC).

! > Task :app:kaptDebugKotlin FAILED
!       ^
 */