package project.csulb.android.finder;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shishir on 4/25/2016.
 */
public class MyAdapter extends ArrayAdapter {
    private List<String> names,addresses,type;

    public MyAdapter(Context context,List<String> name,List<String> address,List<String> type) {
        super(context,R.layout.my_adapter_layout,name);
        this.names = name;
        this.addresses = address;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.my_adapter_layout,parent,false);


        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(names.get(position));

        TextView address = (TextView)view.findViewById(R.id.address);
        String add = addresses.get(position);
        address.setText(add);

        TextView distance = (TextView)view.findViewById(R.id.distance);
        String typeOfItem = type.get(position);
        distance.setText(typeOfItem);

        return view;
    }
}
