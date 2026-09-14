package com.example.circuitdoctor;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "circuit_doctor_db")
                    .allowMainThreadQueries() // ใช้สำหรับทดสอบการคิวรีอย่างง่าย
                    .build();
        }
        return INSTANCE;
    }
}