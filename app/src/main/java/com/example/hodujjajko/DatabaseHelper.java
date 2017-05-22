package com.example.hodujjajko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance = null;
    public static final String DATABASE_NAME = "database";
    public static final String TABLE_TRAINING="training"; // tutaj musimy dac nazwy tabeli jakie chcemy
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_TYPE_OF_TRAINING="typeOfTraining";
    public static final String COLUMN_START="start";
    public static final String COLUMN_FINISH="finish";
    public static final String COLUMN_POINTS="points";
    public static final String COLUMN_DURATION="duration";
    public static final String COLUMN_IS_DONE="isDone";

    private static final String DATABASE_CREATE = "create table " + TABLE_TRAINING + "("+ COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TYPE_OF_TRAINING +"varchar(20), "
            + COLUMN_START + "datetime," + COLUMN_FINISH + "datetime," + COLUMN_POINTS + "integer,"
            + COLUMN_DURATION + "integer ," + COLUMN_IS_DONE + " boolean;";

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
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        onCreate(db);

    }
}
