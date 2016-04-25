package project.csulb.android.finder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;

    ImageButton atm,hospital,gas,restaurant;
    TextView atm_name,hospital_name,gas_name,restautant_name;
    double latitude,longitude;
    Get_Location locationObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        if((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        //Log.i("Location", Double.toString(latitude));


        atm = (ImageButton)findViewById(R.id.atm);
        gas = (ImageButton)findViewById(R.id.gas_station);
        hospital = (ImageButton)findViewById(R.id.hospital);
        restaurant = (ImageButton)findViewById(R.id.restaurant);

        getLatitude();
        getLongitude();

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Log.i("Latitude",Double.toString(latitude));
                    Log.i("Longitude",Double.toString(longitude));

                    Intent intent = new Intent(getApplicationContext(),Atm_activity.class);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude", longitude);

                    startActivity(intent);


            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GasStation_activity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);

                startActivity(intent);
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Hospital_activity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);

                startActivity(intent);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Restaurant_activity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);

                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_uninstall) {
            Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:project.csulb.android.finder"));
            startActivity(intent);
        }
        if(id == R.id.action_searches){
            Log.i("Recent Searches","Recent Searches" );
        }

        return super.onOptionsItemSelected(item);
    }

    public void getLatitude(){
        locationObj = new Get_Location(MainActivity.this);
        if(locationObj.canGetLocation()) {
            latitude =  locationObj.getLatitude();
        }
        else {
            Log.i("main","showing settings");
            locationObj.showSettings();
        }
    }
    public void getLongitude(){
        locationObj = new Get_Location(MainActivity.this);
        if(locationObj.canGetLocation()) {
            longitude =  locationObj.getLongitude();
        }
        else {
            Log.i("main","showing settings");
            locationObj.showSettings();
        }
    }
}
