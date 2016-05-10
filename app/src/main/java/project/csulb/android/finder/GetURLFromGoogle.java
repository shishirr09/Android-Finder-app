package project.csulb.android.finder;

/**
 * Created by Shishir on 5/9/2016.
 */
public class GetURLFromGoogle {
    private static final String key = "AIzaSyDP95xytaMqzlvkJFC0zciE5lwveWhfrhM";
    private static final String output = "json";
    private static final String API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private double latitude;
    private double longitude;

    public GetURLFromGoogle(double lat, double lng){
        this.latitude = lat;
        this.longitude = lng;
    }

    public String getRestaurantURL(){
        return API_URL +output +"?location="+latitude+","+longitude+"&radius=500&type=restaurant&key="+key;
    }
}
