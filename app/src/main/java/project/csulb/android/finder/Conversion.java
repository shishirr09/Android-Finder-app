package project.csulb.android.finder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
        String address = "";
        Geocoder gc = new Geocoder(context, Locale.getDefault());
        try{
            list = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
        }catch (IOException e){}

        if (list.get(0) != null){
            address = list.get(0).getAddressLine(0) +", "+ list.get(0).getAddressLine(1) + ", "+ list.get(0).getAddressLine(2);
        }
        return address;
    }

    public Location getLocationFromAddress(String address){
        List<Address> list = null;
        Address ad ;

        Geocoder gc = new Geocoder(context);
        try{
            list = gc.getFromLocationName(address, 5);
        }catch (IOException e){}

        if(list.isEmpty()){
            return new Location("");
        }
        ad = list.get(0);

        Location location = new Location("");
        location.setLatitude(ad.getLatitude());
        location.setLongitude(ad.getLongitude());
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
        return (Double.toString(dist) + " m");
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
