package com.example.hodujjajko;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 2017-05-20.
 */

public class TrainingDao implements ITrainingDAO{
    public TrainingDao()
    {
        // jeśli zrobić tu dziedziczenie po DBContentProvider to będzie super(db);
    }
    @Override
    public boolean addTraining(Training training)
    {
        return false;
    }

    @Override
    public boolean deleteTraining(Training training)
    {
        return false;
    }

    @Override
    public Training fetchTrainingById(int trainingId)
    {
        return null;
    }

    @Override
    public List<Training> fetchAllData() {
        List<Training> trainingList = new ArrayList<Training>();
        // tutaj cursor jeśli znowu będe dziedziczyć po Provider???
        return null;
    }
}
