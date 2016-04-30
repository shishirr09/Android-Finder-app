package project.csulb.android.finder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shishir on 4/29/2016.
 */
public class ServiceHandler  {
    public String makeServiceCall(String urlString) {
        StringBuilder builder = new StringBuilder();
        URL url ;
        HttpURLConnection connection;
        try{
            Log.i("URL", urlString);
            url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println(code);
            if(code == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine())!=null){
                    builder.append(line);
                }
            }
            else{
                Log.e("Tag", "Failed to download file");
            }


        }catch (Exception e){e.printStackTrace();}
        return builder.toString();
    }

}

