package project.csulb.android.finder;

/**
 * Created by Shishir on 4/29/2016.
 */
public class GetURL {
    private static final String ClIENT_ID = "VKQP3IVWEFHTOMGHDYUGQL5YVT0SCHLWKPRR5HXYUI1M2N2U";
    private static final String ClIENT_SECRET = "5C54ZSDLIALO4FGDMGCPUQGTCPCSK44AARZ4BAIEXGA3QWJE";
    private static final String API_URL = "https://api.foursquare.com/v2/venues/search?";
    private static final String v = Long.toString(System.currentTimeMillis());
    private static final int limit = 10;
    private double latitude;
    private double longitude;

    public GetURL(double lat, double lng){
        this.latitude = lat;
        this.longitude = lng;
    }



    public String getGas_stationURL(){
        String gas_station = API_URL+"client_id=" + ClIENT_ID + "&client_secret=" + ClIENT_SECRET +
                "&v="+v+"&ll="+latitude+","+longitude+ "&query=\"Gas station\" &limit="+limit+"\"";
        return gas_station;
    }

    public String getATMURL(){
        String atm = API_URL+"client_id=" + ClIENT_ID + "&client_secret=" + ClIENT_SECRET +
                "&v="+v+"&ll="+latitude+","+longitude+ "&query=\"ATM\" &limit="+limit+"\"";
        return atm;
    }

    public String getRestaurantURL(){
        String restaurant = API_URL+"client_id=" + ClIENT_ID + "&client_secret=" + ClIENT_SECRET +
                "&v="+v+"&ll="+latitude+","+longitude+ "&query=\"Restaurant\" &limit="+limit+"\"";
        return restaurant;
    }

    public String getHospitalURL(){
        String restaurant = API_URL+"client_id=" + ClIENT_ID + "&client_secret=" + ClIENT_SECRET +
                "&v="+v+"&ll="+latitude+","+longitude+ "&query=\"Hospital\" &limit="+limit+"\"";
        return restaurant;
    }
}
