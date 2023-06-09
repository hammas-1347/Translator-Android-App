package com.translator;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {LanguageEntity.class},version =1)
public abstract class LanguageDataBase extends RoomDatabase {

    public static LanguageDataBase Instance;
    public static LanguageDataBase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(),
                    LanguageDataBase.class,"Portal DataBase").
                    allowMainThreadQueries().build();
        }
        return Instance;
    }

    public abstract DaoInterface daoInterface();

}