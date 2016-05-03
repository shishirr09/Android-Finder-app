package project.csulb.android.finder;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Shishir on 4/7/2016.
 */
public class Get_Location  extends Service implements LocationListener{
    private Context mContext;
    private Location location;
    private double latitude, longitude;
    private LocationManager manager ;
    private boolean canGetLocation = false;
    private boolean isGPSEnabled = false, isNetworkEnabled = false;
    private long Min_Time_Between_Updates = 1000*60*1;
    private float Min_Distance_For_Updates = 10;

    public Get_Location(Context context){
        this.mContext = context;
        getLocation();

    }

    public double getLatitude(){
        if(location != null){
            latitude =  location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude =  location.getLongitude();
        }
        return longitude;
    }
    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettings(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Gps is disabled");
        dialog.setMessage("Would you like to enable GPS");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        dialog.show();

    }
    private Location getLocation() {
        try {

            manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                Log.i("Provider", "No provider available");
            }
            else{
                this.canGetLocation = true;

                if(isNetworkEnabled){

                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Min_Time_Between_Updates, Min_Distance_For_Updates, this);
                    if(manager!= null){
                        location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location!=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if(location == null){
                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_Time_Between_Updates, Min_Distance_For_Updates, this);
                        //System.out.println("print");
                        Log.d("Gps enabled","Gps enabled");
                        if(manager!= null){
                            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location!=null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



