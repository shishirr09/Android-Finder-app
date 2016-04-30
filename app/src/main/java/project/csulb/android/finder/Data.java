package project.csulb.android.finder;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shishir on 4/29/2016.
 */
public class Data{
    private ArrayList<String> names;
    private ArrayList<Location> locations;

    public Data(){
        names = new ArrayList<>();
        locations = new ArrayList<>();
    }

    public void addName(String name){
        names.add(name);
    }

    public void addLocation(double lat, double lng){
        Location loc = new Location("");
        loc.setLatitude(lat);
        loc.setLongitude(lng);

        locations.add(loc);
    }

    public ArrayList<String> getNames(){
        return names;
    }

    public ArrayList<Location> getLocations(){
        return locations;
    }
}

