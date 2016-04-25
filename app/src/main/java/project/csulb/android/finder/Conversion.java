package project.csulb.android.finder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shishir on 4/7/2016.
 */
public class Conversion {
    private Context context ;

    public Conversion(Context context){
        this.context = context;
    }

    public String getAddressFromLocation(Location location){
        List<Address> list = null;

        Geocoder gc = new Geocoder(context, Locale.getDefault());
        try{
            list = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
        }catch (IOException e){}
        return list.get(0).toString();
    }

    public Location getLocationFromAddress(String address){
        List<Address> list = null;
        Address ad ;

        Geocoder gc = new Geocoder(context);
        try{
            list = gc.getFromLocationName(address, 5);
        }catch (IOException e){}

            ad = list.get(0);


        Log.i("In getLocationFromAdd",ad.toString());
        double latitude = ad.getLatitude();
        double longitude = ad.getLongitude();
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public String getDistance(Location loc1, Location loc2){
        double lat1 = loc1.getLatitude();
        double lon1 = loc1.getLongitude();
        double lat2 = loc2.getLatitude();
        double lon2 = loc2.getLongitude();

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // Rounding distance to two decimal places
        dist = Math.floor(dist*100)/100;
        Log.i("Distance",Double.toString(dist));

        return (Double.toString(dist) + " m");

    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
