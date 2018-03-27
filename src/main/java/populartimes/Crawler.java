package populartimes;

import com.google.gson.*;
import model.Popularity;
import org.apache.http.client.utils.URIBuilder;
import response.GoogleMapResponse;
import response.GooglePlacesResponse;
import util.Constants;
import util.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Crawler {

    GoogleMapResponse googleMapResponse;

    protected String getPlace(String placeId, String apiKey) throws URISyntaxException {
        String placeIdentifier = getPlaceIdentifier(placeId, apiKey);
        return getPlacePopularity(placeIdentifier);
    }

    private String getPlaceIdentifier (String placeId, String apiKey){
        try{
            String URL = String.format(Constants.URL_GOOGLE_MAP, placeId, apiKey);
            googleMapResponse = new HttpRequest < GoogleMapResponse >().request(URL, GoogleMapResponse.class);
            return googleMapResponse.getPlaceIdentifier();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    private String getPlacePopularity(String placeIdentifier) throws URISyntaxException {
        try{
            GooglePlacesResponse placesResponse = new HttpRequest<GooglePlacesResponse>().request(urlBuilder(placeIdentifier), GooglePlacesResponse.class);
            JsonArray info = getPopularityArray(placesResponse);
            Map<String, int[]> populartimes = getPopularityForWeek(info.get(84).getAsJsonArray().get(0).getAsJsonArray());
            //TODO null check
            JsonArray ratingArray = info.get(4).getAsJsonArray();
            String place_info = info.get(117).getAsJsonArray().get(0).getAsString();
            int rating   = ratingArray.get(7).getAsInt();
            int rating_n = ratingArray.get(8).getAsInt();
            return generateJson(setPopularity(populartimes, rating, rating_n, place_info));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private  JsonArray getPopularityArray(GooglePlacesResponse placesResponse) {
        JsonParser jsonParser = new JsonParser();
        JsonArray arrayFromString = jsonParser.parse(placesResponse.getD()).getAsJsonArray();
        return arrayFromString.get(0).getAsJsonArray().get(1).getAsJsonArray().get(0).getAsJsonArray().get(14).getAsJsonArray();
    }

    private Popularity setPopularity(Map<String, int[]> populartimes, int rating, int rating_n, String place_info){
        Popularity popularity = new Popularity();
        popularity.setPopulartimes(populartimes);
        popularity.setPlace_id(googleMapResponse.getPlaceId());
        popularity.setPlace_name(googleMapResponse.getPlaceName());
        popularity.setTypes(googleMapResponse.getTypes());
        popularity.setFormatted_address(googleMapResponse.getFormatedAddress());
        popularity.setRatings(rating);
        popularity.setRatings_n(rating_n);
        popularity.setPlace_info(place_info);

        return popularity;
    }

    private String generateJson(Popularity popularities){
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonResult = gsonBuilder.toJson(popularities);

        return jsonResult;
    }

    private  Map<String, int[]> getPopularityForWeek(JsonArray weekData){
        Map<String,int[]> dailyPopularities = new HashMap<>();
        for (JsonElement dailyData : weekData){
            int [] visitors = new int[24];
            JsonArray item = dailyData.getAsJsonArray();
            String dayName = Constants.dayOfWeeks[item.get(0).getAsInt() - 1];
            for(JsonElement popularity : item.get(1).getAsJsonArray()){
               JsonArray popularities = popularity.getAsJsonArray();
               visitors[popularities.get(0).getAsInt()] = popularities.get(1).getAsInt();
            }
            dailyPopularities.put(dayName,visitors);
        }
        return dailyPopularities;
    }


    private  String urlBuilder(String place_identifier) throws URISyntaxException {
        String baseUrl = "https://www.google.fr/search?";
        URIBuilder buildedUrl = new URIBuilder(baseUrl);
        buildedUrl.addParameter("tbm","map");
        buildedUrl.addParameter("tch","1");
        buildedUrl.addParameter("q",place_identifier);
        String url = buildedUrl.toString()+"&pb=!4m12!1m3!1d4005.9771522653964!2d-122.42072974863942!3d37.8077459796541!2m3!1f0!2f0!3f0!3m2!1i1125!2i976"+
                "!4f13.1!7i20!10b1!12m6!2m3!5m1!6e2!20e3!10b1!16b1!19m3!2m2!1i392!2i106!20m61!2m2!1i203!2i100!3m2!2i4!5b1"+
                "!6m6!1m2!1i86!2i86!1m2!1i408!2i200!7m46!1m3!1e1!2b0!3e3!1m3!1e2!2b1!3e2!1m3!1e2!2b0!3e3!1m3!1e3!2b0!3e3!"+
                "1m3!1e4!2b0!3e3!1m3!1e8!2b0!3e3!1m3!1e3!2b1!3e2!1m3!1e9!2b1!3e2!1m3!1e10!2b0!3e3!1m3!1e10!2b1!3e2!1m3!1e"+
                "10!2b0!3e4!2b1!4b1!9b0!22m6!1sa9fVWea_MsX8adX8j8AE%3A1!2zMWk6Mix0OjExODg3LGU6MSxwOmE5ZlZXZWFfTXNYOGFkWDh"+
                "qOEFFOjE!7e81!12e3!17sa9fVWea_MsX8adX8j8AE%3A564!18e15!24m15!2b1!5m4!2b1!3b1!5b1!6b1!10m1!8e3!17b1!24b1!"+
                "25b1!26b1!30m1!2b1!36b1!26m3!2m2!1i80!2i92!30m28!1m6!1m2!1i0!2i0!2m2!1i458!2i976!1m6!1m2!1i1075!2i0!2m2!"+
                "1i1125!2i976!1m6!1m2!1i0!2i0!2m2!1i1125!2i20!1m6!1m2!1i0!2i956!2m2!1i1125!2i976!37m1!1e81!42b1!47m0!49m1"+
                "!3b1";

        return url;
    }

}
