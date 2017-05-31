package com.example.hodujjajko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 2017-05-20.
 */

public class TrainingDao implements ITrainingDAO{
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    ContentValues initialValues = new ContentValues();
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_TYPE_OF_TRAINING, DatabaseHelper.COLUMN_START,
            DatabaseHelper.COLUMN_FINISH, DatabaseHelper.COLUMN_POINTS,DatabaseHelper.COLUMN_DURATION};

    public TrainingDao(Context context)
    {
        databaseHelper = DatabaseHelper.getInstance(context);
    }
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }
    public void close() {
        databaseHelper.close();
    }

    @Override
    public boolean addTraining(Training training)
    {
        Log.i("Dataabase","dodano");
        setContentValue(training);
        try {
            return database.insert(DatabaseHelper.TABLE_TRAINING, null, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public void deleteTraining(Training training)
    {
        long id = training.id;
        database.delete(DatabaseHelper.TABLE_TRAINING, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    @Override
    public Training fetchTrainingById(int trainingId)
    {
        Training training = new Training();
        Cursor cursor = database.rawQuery("SELECT * FROM" + DatabaseHelper.TABLE_TRAINING + "WHERE" + DatabaseHelper.COLUMN_ID
                + " = " + trainingId, null );

        training = cursorToTraining(cursor);
        return training;
    }

    @Override
    public List<Training> fetchAllData() {
        List<Training> trainingList = new ArrayList<Training>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_TRAINING,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Training training = cursorToTraining(cursor);
            trainingList.add(training);
            cursor.moveToNext();
        }
        cursor.close();
        return trainingList;
    }
    private Training cursorToTraining (Cursor cursor) {
        Training training= new Training();
        training.id = cursor.getInt(0);
        training.typeOfTraining = cursor.getString(1);
        training.start = cursor.getString(2);
        training.finish = cursor.getString(3);
        training.points = cursor.getInt(4);
        training.duration = cursor.getString(5);


        return training;
    }

    private void setContentValue(Training training) {
        initialValues.put(DatabaseHelper.COLUMN_TYPE_OF_TRAINING, training.typeOfTraining);
        initialValues.put(DatabaseHelper.COLUMN_START, training.start);
        initialValues.put(DatabaseHelper.COLUMN_FINISH, training.finish);
        initialValues.put(DatabaseHelper.COLUMN_DURATION, training.duration);
        initialValues.put(DatabaseHelper.COLUMN_POINTS, training.points);

    }
    private ContentValues getContentValue() {
        return initialValues;
    }


}
