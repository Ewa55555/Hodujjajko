package com.example.hodujjajko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class LocationDAO implements ILocationDAO {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    ContentValues initialValues = new ContentValues();
    private String[] allColumns = { DatabaseHelper.COLUMN_ID_LOCATION, DatabaseHelper.COLUMN_LATITUDE,
                                    DatabaseHelper.COLUMN_LONGITUDE};
    public LocationDAO(Context context)
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
    public boolean addLocation(Location location) {
        setContentValue(location);
        try {
            return database.insert(DatabaseHelper.TABLE_LOCATION, null, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }

    }

    @Override
    public void deleteLocation(Location location) {
        long id = location.id;
        database.delete(DatabaseHelper.TABLE_LOCATION, DatabaseHelper.COLUMN_ID_LOCATION
                + " = " + id, null);
    }

    @Override
    public Location fetchLocationById(int locationId) {

        Location location = new Location();
        Cursor cursor = database.rawQuery("SELECT * FROM" + DatabaseHelper.TABLE_LOCATION + "WHERE" + DatabaseHelper.COLUMN_ID_LOCATION
                + " = " + locationId, null );

        location = cursorToLocation(cursor);
        return location;
    }

    @Override
    public List<Location> fetchAllData() {
        List<Location> locationsList = new ArrayList<Location>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_LOCATION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locationsList.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return locationsList;
    }

    @Override
    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_LOCATION,null,null);
    }

    private Location cursorToLocation (Cursor cursor) {
        Location location= new Location();
        location.id = cursor.getInt(0);
        location.latitude = cursor.getDouble(1);
        location.longitude = cursor.getDouble(2);
        return location;
    }

    private void setContentValue(Location location) {
        initialValues.put(DatabaseHelper.COLUMN_LATITUDE, location.latitude);
        initialValues.put(DatabaseHelper.COLUMN_LONGITUDE, location.longitude);
    }
    private ContentValues getContentValue() {
        return initialValues;
    }
}
