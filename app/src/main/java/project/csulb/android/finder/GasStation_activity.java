package project.csulb.android.finder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shishir on 3/28/2016.
 */
public class GasStation_activity extends AppCompatActivity {
    ListView list;
    List<String> name, address, distance;
    Location currentLocation;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        Intent intent = getIntent();

        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");
        //final DatabaseHelper helper = (DatabaseHelper) intent.getExtras().getSerializable("Database");
        helper = DatabaseHelper.getInstance(getApplicationContext());

        currentLocation = new Location("");
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);

        createData();
        Custom_adapter adapter = new Custom_adapter(this ,name, address,distance, currentLocation);

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),Details.class);

                intent.putExtra("current",currentLocation);
                intent.putExtra("dest", address.get(position));
                intent.putExtra("title", name.get(position));

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.NAME_COLUMN, name.get(position));
                values.put(DatabaseHelper.Address_Column, address.get(position));
                values.put(DatabaseHelper.Type_column, "Gas Station");

                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert(DatabaseHelper.Table_Name, null, values);

                Log.i("dest", address.get(position));
                Log.i("title", name.get(position));
                startActivity(intent);

                /*Log.i("Tag","clicked");
                Toast.makeText(Atm_activity.this,"Position "+position,Toast.LENGTH_SHORT).show();*/
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

    private void createData(){
       /* map = new HashMap<String,String>();
        map.put("Shishir","5050 E Garford Street, Apt #68, Long Beach, 90815 ");
        map.put("Pizza hut","4502 E Los Coyotes Diagonal, Long Beach, CA 90815 ");
        map.put("7 eleven","5109 Pacific Coast Hwy, Long Beach, CA 90804 ");
        map.put("In and Out","4600 Los Coyotes Diagonal, Long Beach, CA 90815 ");
        map.put("CSULB","1250 Bellflower Blvd, Long Beach, CA 90840 ");*/

        name = new ArrayList<String>();
        address = new ArrayList<String>();
        distance = new ArrayList<>();

        Conversion obj = new Conversion(getApplicationContext());

        // Check for address, some address do not return location

        name.add("Shishir");
        address.add("5050 E Garford Street, Apt #68, Long Beach, 90815");
        distance.add(obj.getDistance(currentLocation, obj.getLocationFromAddress("5050 E Garford Street, Apt #68, Long Beach, 90815")));

        name.add("Pizza hut");
        address.add("4502 E Los Coyotes Diagonal, Long Beach, CA 90815");
        distance.add(obj.getDistance(currentLocation, obj.getLocationFromAddress("4502 E Los Coyotes Diagonal, Long Beach, CA 90815")));


        name.add("7 eleven");
        address.add("5109 Pacific Coast Hwy, Long Beach, CA 90804");
        distance.add(obj.getDistance(currentLocation, obj.getLocationFromAddress("5109 Pacific Coast Hwy, Long Beach, CA 90804")));

        name.add("Edwards cinema");
        address.add("7501 E Carson St, Lakewood, CA 90808");
        distance.add(obj.getDistance(currentLocation, obj.getLocationFromAddress("7501 E Carson St, Lakewood, CA 90808")));

        name.add("CSULB");
        address.add("1250 Bellflower Blvd, Long Beach, CA 90840 ");
        distance.add(obj.getDistance(currentLocation, obj.getLocationFromAddress("1250 Bellflower Blvd, Long Beach, CA 90840")));

        name.add("Pizza hut");
        address.add("4502 E Los Coyotes Diagonal, Long Beach, CA 90815");
        distance.add(obj.getDistance(currentLocation,obj.getLocationFromAddress("4502 E Los Coyotes Diagonal, Long Beach, CA 90815")));

        name.add("Pizza hut");
        address.add("4502 E Los Coyotes Diagonal, Long Beach, CA 90815");
        distance.add(obj.getDistance(currentLocation,obj.getLocationFromAddress("4502 E Los Coyotes Diagonal, Long Beach, CA 90815")));

        name.add("Pizza hut");
        address.add("4502 E Los Coyotes Diagonal, Long Beach, CA 90815");
        distance.add(obj.getDistance(currentLocation,obj.getLocationFromAddress("4502 E Los Coyotes Diagonal, Long Beach, CA 90815")));



    }
}
