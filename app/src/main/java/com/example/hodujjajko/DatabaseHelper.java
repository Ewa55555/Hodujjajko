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
    public static final String TABLE_LOCATION="location";
    public static final String COLUMN_ID_LOCATION="_id";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String TABLE_PLAN="plan";
    public static final String COLUMN_ID_PLAN="_id";
    public static final String COLUMN_DAY_PLAN="day";
    public static final String COLUMN_TIME_START_PLAN="timeStart";
    public static final String COLUMN_DAY_OF_WEEK_PLAN="dayOfWeek";
    public static final String COLUMN_IS_ONCE="isOnce";
    public static final String COLUMN_NAME_PLAN="name";
    public static final String COLUMN_TIME_END_PLAN="timeEnd";



    private static final String CREATE_TRAINIG = "create table " + TABLE_TRAINING + "("+ COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TYPE_OF_TRAINING +" text, "
            + COLUMN_START + " text, " + COLUMN_FINISH + " text, " + COLUMN_POINTS + " integer, "
            + COLUMN_DURATION + " integer ," + COLUMN_IS_DONE + " integer);";

    private static final String CREATE_LOCATION = "create table " + TABLE_LOCATION + "("+ COLUMN_ID_LOCATION
            + " integer primary key autoincrement, " + COLUMN_LONGITUDE + " real," + COLUMN_LATITUDE +" real);";

    private static final String CREATE_PLAN = "create table " + TABLE_PLAN + "("+ COLUMN_ID_PLAN
            + " integer primary key autoincrement, " +COLUMN_NAME_PLAN + " text, " + COLUMN_DAY_PLAN +" text, "
            + COLUMN_TIME_START_PLAN + " text, " + COLUMN_TIME_END_PLAN + " text, " + COLUMN_DAY_OF_WEEK_PLAN + " text, " + COLUMN_IS_ONCE+ " integer);";

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
        db.execSQL(CREATE_TRAINIG);
        db.execSQL(CREATE_LOCATION);
        db.execSQL(CREATE_PLAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        onCreate(db);

    }
}
