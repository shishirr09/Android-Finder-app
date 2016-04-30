package project.csulb.android.finder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    List<String> names ;
    List<String> addresses ;
    List<String> type ;
    Location currentLocation;
    ListView view;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        names  = new ArrayList<>();
        addresses =  new ArrayList<>();
        type = new ArrayList<>();

        Intent intent = getIntent();
        //DatabaseHelper helper = (DatabaseHelper) intent.getExtras().getSerializable("Database");
        helper = DatabaseHelper.getInstance(getApplicationContext());

        currentLocation = new Location("");
        currentLocation.setLatitude(intent.getExtras().getDouble("latitude"));
        currentLocation.setLongitude(intent.getExtras().getDouble("longitude"));

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
                Intent intent = new Intent(getApplicationContext(),Details.class);

                intent.putExtra("current",currentLocation);
                intent.putExtra("dest",addresses.get(position));
                intent.putExtra("title", names.get(position));

                Log.i("dest", addresses.get(position));
                Log.i("title", names.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recent_searches_menu,menu);
        return true;
    }
}
