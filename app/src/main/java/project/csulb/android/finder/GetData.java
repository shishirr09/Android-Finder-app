package project.csulb.android.finder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.URL;


/**
 * Created by Shishir on 4/29/2016.
 */
public class GetData extends AsyncTask<String,Void,Data> {

    private Data data;

    public GetData() {
        data = new Data();
    }

    @Override
    protected Data doInBackground(String... params) {
        String url = params[0];

        ServiceHandler sh = new ServiceHandler();
        String jsonStr = sh.makeServiceCall(url);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject obj = new JSONObject(jsonStr);
                JSONArray arr = obj.getJSONObject("response").getJSONArray("venues");

                for (int i = 0; i < 10; i++) {
                    addName(i, arr);
                    addLocation(i,arr);
                    addContacts(i,arr);
                    addImage(i, arr);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return data;
    }

    private void addName(int i, JSONArray arr){

        String name;
        try {
            name = arr.getJSONObject(i).getString("name");
            data.addName(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addLocation(int i, JSONArray arr){
        try {
            double latitude = arr.getJSONObject(i).getJSONObject("location").getDouble("lat");
            double longitude = arr.getJSONObject(i).getJSONObject("location").getDouble("lng");
            data.addLocation(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addContacts(int i, JSONArray arr){
        String contact;
        try{
            if(arr.getJSONObject(i).getJSONObject("contact").has("phone")){
                contact = arr.getJSONObject(i).getJSONObject("contact").getString("phone");
            }
            else {
                contact = null;
            }

            data.addContacts(contact);
        }catch (Exception e){e.printStackTrace();}
    }

    private void addImage(int i, JSONArray arr){
        String imageURL = null;
        String defaultURL = "https://foursquare.com/img/categories/food/default_88.png";
        Bitmap bmp;

        try{
            JSONArray array = arr.getJSONObject(i).getJSONArray("categories");

            if(array.length() > 0){
                System.out.println(i +": "+ array.length());
                JSONObject object = array.getJSONObject(0);
                if(object.has("icon")){
                    if(object.getJSONObject("icon").has("prefix") && object.getJSONObject("icon").has("suffix")){
                        imageURL = object.getJSONObject("icon").getString("prefix")+ "bg_88" + object.getJSONObject("icon").getString("suffix");
                    }
                }
            }

            if(imageURL != null){
                URL url;
                try{
                    url = new URL(imageURL);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                }catch (FileNotFoundException e){
                    url = new URL(defaultURL);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    e.printStackTrace();
                }

            }
            else{
                URL url = new URL(defaultURL);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }

            data.addImage(bmp);
        }catch (Exception e){e.printStackTrace();}
    }
}
