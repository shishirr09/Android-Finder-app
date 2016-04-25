package project.csulb.android.finder;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Shishir on 4/7/2016.
 */
public class Details extends AppCompatActivity {
    Button getDirections;
    Location currentLocation, destinationLocation;
    double currentLat, currentLong, destLat, destLong;
    TextView titleName, address, website, contact;
    String tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);
        getDirections = (Button) findViewById(R.id.imageButton);

        Intent intent = getIntent();
        currentLocation = (Location) intent.getExtras().get("current");
        currentLat = currentLocation.getLatitude();
        currentLong = currentLocation.getLongitude();


        String destAddress = intent.getStringExtra("dest");
        tittle = intent.getStringExtra("title");

        titleName = (TextView)findViewById(R.id.title);
        titleName.setText(tittle);

        address = (TextView)findViewById(R.id.address);
        address.setText(destAddress);

        Conversion obj = new Conversion(getApplicationContext());
        destinationLocation = obj.getLocationFromAddress(destAddress);

        final String destinationAddress = obj.getAddressFromLocation(destinationLocation);

        destLat = destinationLocation.getLatitude();
        destLong = destinationLocation.getLongitude();


        getDirections = (Button)findViewById(R.id.imageButton);
        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+currentLat+","+currentLong+"&daddr="+destLat+","+destLong+"\"");

                String url = "http://maps.google.com/maps?daddr="+destinationAddress;
                Uri uri = Uri.parse(url);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }
}
