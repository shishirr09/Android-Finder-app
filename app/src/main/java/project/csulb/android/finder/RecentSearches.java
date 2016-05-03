package project.csulb.android.finder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shishir on 4/25/2016.
 */
public class RecentSearches extends AppCompatActivity {
    private List<String> names ;
    private List<String> addresses ;
    private List<String> type ;
    private Location currentLocation;
    private ListView view;
    private DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        names  = new ArrayList<>();
        addresses =  new ArrayList<>();
        type = new ArrayList<>();

        Intent intent = getIntent();
        helper = DatabaseHelper.getInstance(getApplicationContext());

        final double latitude = intent.getExtras().getDouble("latitude");
        final double longitude = intent.getExtras().getDouble("longitude");

        currentLocation = new Location("");
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * "+" FROM "+DatabaseHelper.Table_Name+" ORDER BY "+ DatabaseHelper.ID_COLUMN+" DESC;";
        Cursor cr = db.rawQuery(sql,null);

        if(cr.moveToFirst()){
            do{
                names.add(cr.getString(cr.getColumnIndex(DatabaseHelper.NAME_COLUMN)));
                addresses.add(cr.getString(cr.getColumnIndex(DatabaseHelper.Address_Column)));
                type.add(cr.getString(cr.getColumnIndex(DatabaseHelper.Type_column)));

            }while (cr.moveToNext());
        }

        MyAdapter adapter = new MyAdapter(this,names,addresses,type);
        view = (ListView)findViewById(R.id.list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Conversion obj = new Conversion(getApplicationContext());
                Location loc = obj.getLocationFromAddress(addresses.get(position));
                double destLat = loc.getLatitude();
                double destLong = loc.getLongitude();

                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + destLat + "," + destLong + "\"");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recent_searches_menu,menu);
        return true;
    }
}
