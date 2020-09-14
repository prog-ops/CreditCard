package com.example.empatkali.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = Item.class,
        version = 1,
        exportSchema = false
)
public abstract class RoomDb extends RoomDatabase {
    private static RoomDb INSTANCE;

    public abstract ItemDAO getItemDAO();

    public static RoomDb getDB(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDb.class,
                    RoomDb.class.getName())
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
