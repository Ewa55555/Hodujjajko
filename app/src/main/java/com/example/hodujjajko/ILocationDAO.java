package com.example.hodujjajko;

import java.util.List;


public interface ILocationDAO {
    public boolean addLocation(Location location);
    public void deleteLocation(Location location);
    public Location fetchLocationById(int locationId);
    public List<Location> fetchAllData();
    public void deleteAll();
}
