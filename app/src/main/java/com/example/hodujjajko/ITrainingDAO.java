package com.example.hodujjajko;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Kasia on 2017-05-20.
 */

public interface ITrainingDAO {
    public boolean addTraining(Training training);
    public void deleteTraining(Training training);
    public Training fetchTrainingById(int trainingId);
    public List<Training> fetchAllData();
}
