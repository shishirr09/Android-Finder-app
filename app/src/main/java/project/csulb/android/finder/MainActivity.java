package project.csulb.android.finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   /* public void onClick(View v) {
        if(v.getId() == atm.getId()){
            startActivity(new Intent(this,Atm_activity.class));
        }
        else if(v.getId() == gas.getId()){
            startActivity(new Intent(this,GasStation_activity.class));

        }
        else if(v.getId() == hospital.getId()){
            startActivity(new Intent(this,Hospital_activity.class));

        }
        else if(v.getId() == restaurant.getId()){
            startActivity(new Intent(this,Restaurant_activity.class));

        }
    }*/

    ImageButton atm,hospital,gas,restaurant;
    TextView atm_name,hospital_name,gas_name,restautant_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atm = (ImageButton)findViewById(R.id.atm);
        gas = (ImageButton)findViewById(R.id.gas_station);
        hospital = (ImageButton)findViewById(R.id.hospital);
        restaurant = (ImageButton)findViewById(R.id.restaurant);

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Atm_activity.class));
            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GasStation_activity.class));
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Hospital_activity.class));
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Restaurant_activity.class));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
