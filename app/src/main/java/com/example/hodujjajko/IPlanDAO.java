package com.example.hodujjajko;

import java.util.List;

/**
 * Created by Kasia on 2017-05-24.
 */

public interface IPlanDAO {
    public boolean addPlan(Plan plan);
    public void deletePlan(Plan plan);
    public Plan fetchPlanById(int planId);
    public List<Plan> fetchAllData();
    public void deleteAll();
}
