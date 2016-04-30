package project.csulb.android.finder;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
                    addContact(i,arr);
                    addIcon(i,arr);

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

    private void addContact(int i, JSONArray arr){

    }

    private void addIcon(int i, JSONArray arr){
        
    }
}
