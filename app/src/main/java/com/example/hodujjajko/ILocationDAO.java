package com.example.hodujjajko;

import java.util.List;

/**
 * Created by Ewunia on 24.05.2017.
 */

public interface ILocationDAO {
    public boolean addLocation(Location location);
    public void deleteLocation(Location location);
    public Location fetchLocationById(int locationId);
    public List<Location> fetchAllData();
    public void deleteAll();
}
