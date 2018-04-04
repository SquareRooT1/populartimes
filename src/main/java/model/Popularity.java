package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Popularity {
    @JsonProperty("place_id")
    private  String         placeId;
    @JsonProperty("place_name")
    private  String         placeName;
    @JsonProperty("formatted_address")
    private  String         formattedAddress;
    @JsonProperty("types")
    private  List<String>   types;
    @JsonProperty("ratings")
    private  int            ratings;
    @JsonProperty("ratings_n")
    private  int            ratingsN;
    @JsonProperty("populartimes")
    private  Map <String, int[]> populartimes;
    @JsonProperty("place_info")
    private  String         placeInfo;
    @JsonProperty("reviews")
    private  List<Reviews>  reviews;


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getRatingsN() {
        return ratingsN;
    }

    public void setRatingsN(int ratingsN) {
        this.ratingsN = ratingsN;
    }

    public Map<String, int[]> getPopulartimes() {
        return populartimes;
    }

    public void setPopulartimes(Map<String, int[]> populartimes) {
        this.populartimes = populartimes;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
}
