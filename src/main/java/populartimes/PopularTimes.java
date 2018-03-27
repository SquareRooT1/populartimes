package populartimes;

import util.Constants;

import java.net.URISyntaxException;
import java.security.InvalidParameterException;

public class PopularTimes {

    private String apiKey = "";

    public PopularTimes(String apiKey){
        this.apiKey = apiKey;
    }

    public PopularTimes(){
        if(Constants.APIKEY.length() > 0){
            this.apiKey = Constants.APIKEY;
        }else{
            throw new InvalidParameterException("You need to set your google maps api key!!!");
        }

    }

    public String getPlace(String placeId) throws URISyntaxException {
        Crawler crawler = new Crawler();
        return crawler.getPlace(placeId, apiKey);
    }


}
