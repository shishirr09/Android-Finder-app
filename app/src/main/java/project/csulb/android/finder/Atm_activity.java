package project.csulb.android.finder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shishir on 3/28/2016.
 */
public class Atm_activity extends AppCompatActivity {
    ListView list;
    List<String> names, addresses, distance,contacts;
    ArrayList<Bitmap> images;
    Location currentLocation;
    DatabaseHelper helper;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        Intent intent = getIntent();

        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");
        helper = DatabaseHelper.getInstance(getApplicationContext());

        currentLocation = new Location("");
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);


        createData(latitude, longitude);
        calData();

        Custom_adapter adapter = new Custom_adapter(this ,names, addresses, distance,contacts,images);

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Conversion obj = new Conversion(getApplicationContext());
                Location loc = obj.getLocationFromAddress(addresses.get(position));
                double destLat = loc.getLatitude();
                double destLong = loc.getLongitude();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.NAME_COLUMN, names.get(position));
                values.put(DatabaseHelper.Address_Column, addresses.get(position));
                values.put(DatabaseHelper.Type_column, "Atm");

                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert(DatabaseHelper.Table_Name, null, values);

                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + destLat + "," + destLong + "\"");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.atm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.unistall) {
            Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:project.csulb.android.finder"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void createData(double lat, double lng) {

        GetData obj = new GetData();
        obj.execute(new GetURL(lat, lng).getATMURL());

        try {
            data = obj.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void calData(){
        names = data.getNames();
        contacts = data.getContacts();
        images = data.getImages();
        addresses = data.getAddress(new Conversion(getApplicationContext()));
        distance = data.getDistance(new Conversion(getApplicationContext()), currentLocation);

    }
}
