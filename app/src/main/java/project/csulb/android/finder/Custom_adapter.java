package project.csulb.android.finder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shishir on 3/28/2016.
 */
public class Custom_adapter extends ArrayAdapter {
    private List<String> names,addresses,dist,contacts;
    private ArrayList<Bitmap> images;
    public Custom_adapter(Context context,List<String> name,List<String> address,List<String> distance,List<String> contacts,ArrayList<Bitmap> images) {
        super(context,R.layout.custom_adapter_layout,name);
        this.names = name;
        this.addresses = address;
        this.dist = distance;
        this.contacts = contacts;
        this.images = images;
    }


    @Override
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

        ImageView img = (ImageView)view.findViewById(R.id.image);
        img.setImageBitmap(images.get(position));

        TextView contact = (TextView)view.findViewById(R.id.contact);
        if(contacts.get(position) == null){
            contact.setText("No contact available");
        }
        else {
            contact.setText("Contact : "+contacts.get(position));
        }
        return view;
    }

}
