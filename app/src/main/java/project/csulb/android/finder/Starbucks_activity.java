package project.csulb.android.finder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shishir on 5/2/2016.
 */
public class Starbucks_activity extends AppCompatActivity {
    ListView list;
    ArrayList<String> names, addresses, distance,contacts;
    ArrayList<Bitmap> images;
    Location currentLocation;
    DatabaseHelper helper;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        Intent intent = getIntent();
        final ActivityHelper activityHelper = new ActivityHelper(intent);
        helper = DatabaseHelper.getInstance(getApplicationContext());
        currentLocation = activityHelper.getCurrentLocation();


        createData(activityHelper.getLatitude(), activityHelper.getLongitude());
        calData();

        Custom_adapter adapter = new Custom_adapter(this ,names, addresses, distance,contacts,images);

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Location destLoc = activityHelper.getDestinationLocation(addresses, position, getApplicationContext());

                String type = "Starbucks"; // change this
                activityHelper.insertData(names, addresses, type, position, helper);

                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + activityHelper.getLatitude() + "," + activityHelper.getLongitude() + "&daddr=" + destLoc.getLatitude() + "," + destLoc.getLongitude() + "\"");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+contacts.get(position)));
                startActivity(callIntent);
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.starbucks_menu, menu);
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
        obj.execute(new GetURL(lat, lng).getStarbucksURL());

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
