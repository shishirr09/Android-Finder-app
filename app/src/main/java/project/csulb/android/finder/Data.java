package project.csulb.android.finder;

import android.graphics.Bitmap;
import android.location.Location;
import java.util.ArrayList;

/**
 * Created by Shishir on 4/29/2016.
 */
public class Data {
    private ArrayList<String> names;
    private ArrayList<Location> locations;
    private ArrayList<String> contacts;
    private ArrayList<Bitmap> images;

    public Data(){
        names = new ArrayList<>();
        locations = new ArrayList<>();
        contacts = new ArrayList<>();
        images = new ArrayList<>();
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

    public void addContacts(String contact){
        contacts.add(contact);
    }

    public void addImage(Bitmap image){
        images.add(image);
    }

    public ArrayList<String> getNames(){
        return names;
    }

    public ArrayList<String> getContacts(){
        return contacts;
    }

    public ArrayList<Bitmap> getImages(){
        return images;
    }

    public ArrayList<String> getAddress(Conversion obj){
        ArrayList<String> addresses = new ArrayList<>();
        for(Location location : locations){
            addresses.add(obj.getAddressFromLocation(location));
        }
        return addresses;
    }

    public ArrayList<String> getDistance(Conversion obj, Location currentLocation){
        ArrayList<String> distance = new ArrayList<>();
        for(Location location : locations){
            distance.add(obj.getDistance(currentLocation, location));
        }
        return distance;
    }


}
