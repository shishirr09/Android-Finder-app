package project.csulb.android.finder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Shishir on 5/2/2016.
 */
public class ActivityHelper {
    Intent intent;
    public ActivityHelper(Intent intent){
        this.intent = intent;
    }

    public double getLatitude(){
       return intent.getExtras().getDouble("latitude");
    }
    public double getLongitude(){
        return intent.getExtras().getDouble("longitude");
    }

    public Location getCurrentLocation(){
        Location loc = new Location("");
        loc.setLatitude(getLatitude());
        loc.setLongitude(getLongitude());
        return loc;
    }


    public Location getDestinationLocation(ArrayList<String> address, int position, Context context){
        return new Conversion(context).getLocationFromAddress(address.get(position));
    }

    public void insertData(ArrayList<String> names, ArrayList<String> addresses, String type, int position, DatabaseHelper helper){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, names.get(position));
        values.put(DatabaseHelper.Address_Column, addresses.get(position));
        values.put(DatabaseHelper.Type_column, type);

        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(DatabaseHelper.Table_Name, null, values);
    }
}
