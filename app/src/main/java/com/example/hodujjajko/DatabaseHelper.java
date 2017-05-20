package com.example.hodujjajko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kasia on 2017-05-20.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance = null;
    private static final String DATABASE_NAME = "database";
    private static final String DATABASE_TABLE="database_table"; // tutaj musimy dac nazwy tabeli jakie chcemy
    private static final int DATABASE_VERSION =1;


    private DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseHelper(context.getApplicationContext());


        }
        return instance;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //tutaj dodac kod kiedy pierwszy raz tworzy się baza
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion)
        {
            onCreate(db);
        }

    }
}
