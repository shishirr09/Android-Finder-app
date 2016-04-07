package project.csulb.android.finder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Shishir on 3/28/2016.
 */
public class Custom_adapter extends ArrayAdapter {
    HashMap<String,Integer> items;
    public Custom_adapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.display_layout,null);

        TextView item = (TextView)view.findViewById(R.id.diplay_items);
        item.setText(items.get(position));

        TextView distance = (TextView)view.findViewById(R.id.display_distance);
        distance.setText(items.get(position));


        return view;
    }
}
