package com.example.hodujjajko;

import java.util.List;

public interface ITrainingDAO {
    public boolean addTraining(Training training);
    public void deleteTraining(Training training);
    public Training fetchTrainingById(int trainingId);
    public List<Training> fetchAllData();
}
