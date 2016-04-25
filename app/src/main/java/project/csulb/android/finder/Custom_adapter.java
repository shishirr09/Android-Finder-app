package project.csulb.android.finder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shishir on 3/28/2016.
 */
public class Custom_adapter extends ArrayAdapter {
    Location currentLocation;
    List<String> names,addresses,dist;
    public Custom_adapter(Context context,List<String> name,List<String> address,List<String> distance,  Location currentLocation) {
        super(context,R.layout.custom_adapter_layout,name);
        this.names = name;
        this.addresses = address;
        this.currentLocation = currentLocation;
        this.dist = distance;
        System.out.println("In custom adapter");
    }

    @Override

    // will have to order items based on distance
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_adapter_layout,parent,false);


        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(names.get(position));

        TextView address = (TextView)view.findViewById(R.id.address);
        String add = addresses.get(position);
        address.setText(add);

        TextView distance = (TextView)view.findViewById(R.id.distance);
        String distanceTo = dist.get(position);
        distance.setText(distanceTo);


        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return  true;
    }

    @Override
    public boolean isEnabled(int position) {
        //return super.isEnabled(position);
        return true;
    }
}
