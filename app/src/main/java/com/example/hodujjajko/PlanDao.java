package com.example.hodujjajko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlanDao implements IPlanDAO {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    ContentValues initialValues = new ContentValues();
    private String[] allColumns = { DatabaseHelper.COLUMN_ID_PLAN, DatabaseHelper.COLUMN_NAME_PLAN,
            DatabaseHelper.COLUMN_DAY_PLAN, DatabaseHelper.COLUMN_TIME_PLAN,
            DatabaseHelper.COLUMN_DAY_OF_WEEK_PLAN , DatabaseHelper.COLUMN_IS_ONCE};

    public PlanDao(Context context)
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
    public boolean addPlan(Plan plan) {
        setContentValue(plan);
        try {
            Log.i("Database", "dodalem do bazy jeej");
            return database.insert(DatabaseHelper.TABLE_PLAN, null, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.i("Database","pupa" +ex.getMessage());
            return false;
        }
    }

    @Override
    public void deletePlan(Plan plan) {
        long id = plan.id;
        database.delete(DatabaseHelper.TABLE_PLAN, DatabaseHelper.COLUMN_ID_PLAN
                + " = " + id, null);
    }

    @Override
    public Plan fetchPlanById(int planId) {

        Plan plan = new Plan();
        Cursor cursor = database.rawQuery("SELECT * FROM" + DatabaseHelper.TABLE_PLAN + "WHERE" + DatabaseHelper.COLUMN_ID_PLAN
                + " = " + planId, null );

        plan = cursorToPlan(cursor);
        return plan;
    }

    @Override
    public List<Plan> fetchAllData() {
        List<Plan> plansList = new ArrayList<Plan>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_PLAN,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plan plan = cursorToPlan(cursor);
            plansList.add(plan);
            cursor.moveToNext();
        }
        cursor.close();
        return plansList;
    }

    @Override
    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_PLAN,null,null);

    }
    private Plan cursorToPlan (Cursor cursor) {
        Plan plan = new Plan();
        plan.id = cursor.getInt(0);
        plan.name = cursor.getString(1);
        plan.time = cursor.getString(2);
        plan.dayOfWeek = cursor.getString(3);
        plan.isOnce = Boolean.parseBoolean(cursor.getString(4));
        return plan;
    }

    private void setContentValue(Plan plan) {
        initialValues.put(DatabaseHelper.COLUMN_NAME_PLAN, plan.name);
        initialValues.put(DatabaseHelper.COLUMN_DAY_PLAN, plan.day);
        initialValues.put(DatabaseHelper.COLUMN_TIME_PLAN, plan.time);
        initialValues.put(DatabaseHelper.COLUMN_DAY_OF_WEEK_PLAN, plan.dayOfWeek);
        initialValues.put(DatabaseHelper.COLUMN_IS_ONCE, plan.isOnce);


    }
    private ContentValues getContentValue() {
        return initialValues;
    }
}