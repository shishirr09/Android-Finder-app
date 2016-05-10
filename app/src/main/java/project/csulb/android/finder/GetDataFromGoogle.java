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
 * Created by Shishir on 5/9/2016.
 */
public class GetDataFromGoogle extends AsyncTask<String,Void,Data> {
    private Data data;

    public GetDataFromGoogle() {
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
                JSONArray arr = obj.getJSONArray("results");

                for (int i = 0; i < 10; i++) {
                    addLocation(arr, i);
                    addName(arr, i);
                    addIcon(arr, i);
                    addContact();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return data;
    }

    private void addLocation(JSONArray arr, int i) {

        try {
            JSONObject obj = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
            double lat = obj.getDouble("lat");
            double lng = obj.getDouble("lng");

            data.addLocation(lat,lng);
            //System.out.println("Lat " + lat);
            //System.out.println("Lng " + lng);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addName(JSONArray arr, int i) {
        try {
            String name = arr.getJSONObject(i).getString("name");
            data.addName(name);
            //System.out.println("Name " + name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addContact(){
        data.addContacts(null);
    }

    private void addIcon(JSONArray arr, int i) {
        String imageURL = null;
        Bitmap bmp;
        String defaultURL = "https://foursquare.com/img/categories/food/default_88.png";
        try {
            imageURL = arr.getJSONObject(i).getString("icon");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
