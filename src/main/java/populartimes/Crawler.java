package populartimes;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.*;
import model.Live;
import model.Popularity;
import org.apache.http.client.utils.URIBuilder;
import response.GoogleMapResponse;
import response.GooglePlacesResponse;
import util.Constants;
import util.GlobalUtil;
import util.HttpRequest;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
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
            JsonArray weekInfo = info.get(84).getAsJsonArray();
            Live live = new Live();
            if(weekInfo.size() > 6){
                try{
                    live.setInfo(weekInfo.get(6).getAsString());
                    live.setHour(weekInfo.get(7).getAsJsonArray().get(0).getAsInt());
                    live.setValue(weekInfo.get(7).getAsJsonArray().get(1).getAsInt());

                }catch (Exception e){
                    //TODO logger
                }
            }
            Map<String, int[]> populartimes = getPopularityForWeek(weekInfo.get(0).getAsJsonArray());
            //TODO null check
            JsonArray ratingArray = info.get(4).getAsJsonArray();
            String placeInfo = info.get(117).getAsJsonArray().get(0).getAsString();
            int rating   = ratingArray.get(7).getAsInt();
            int ratingN = ratingArray.get(8).getAsInt();
            return generateJson(setPopularity(populartimes, rating, ratingN, placeInfo, live));
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

    private Popularity setPopularity(Map<String, int[]> populartimes, int rating, int ratingN, String placeInfo, Live live){
        Popularity popularity = new Popularity();
        popularity.setPopulartimes(populartimes);
        popularity.setPlaceId(googleMapResponse.getPlaceId());
        popularity.setPlaceName(googleMapResponse.getPlaceName());
        popularity.setTypes(googleMapResponse.getTypes());
        popularity.setFormattedAddress(googleMapResponse.getFormatedAddress());
        popularity.setReviews(googleMapResponse.getReviews());
        popularity.setRatings(rating);
        popularity.setRatingsN(ratingN);
        popularity.setPlaceInfo(placeInfo);
        popularity.setLive(live);

        return popularity;
    }

    private String generateJson(Popularity popularities){
        String jsonResult = null;
        try {
            jsonResult = GlobalUtil.MAPPER.writeValueAsString(popularities);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());//Todo add logger
            return null;
        }

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


    private  String urlBuilder(String placeIdentifier) throws URISyntaxException {
        String baseUrl = "https://www.google.fr/search?";
        URIBuilder buildedUrl = new URIBuilder(baseUrl);
        buildedUrl.addParameter("tbm","map");
        buildedUrl.addParameter("tch","1");
        buildedUrl.addParameter("q",placeIdentifier);
        String url = buildedUrl.toString() + Constants.PROTOBUFFER;

        return url;
    }

}
