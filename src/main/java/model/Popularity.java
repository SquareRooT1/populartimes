package model;

import java.util.List;
import java.util.Map;

public class Popularity {

    private  String         place_id;
    private  String         place_name;
    private  String         formated_address;
    private  List<String>   types;
    private  int            ratings;
    private  int            ratings_n;
    private  Map <String, int[]> populartimes;
    private  String         place_info;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
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

    public int getRatings_n() {
        return ratings_n;
    }

    public void setRatings_n(int ratings_n) {
        this.ratings_n = ratings_n;
    }

    public Map<String, int[]> getPopulartimes() {
        return populartimes;
    }

    public void setPopulartimes(Map<String, int[]> populartimes) {
        this.populartimes = populartimes;
    }

    public String getFormated_address() {
        return formated_address;
    }

    public void setFormated_address(String formated_address) {
        this.formated_address = formated_address;
    }

    public String getPlace_info() {
        return place_info;
    }

    public void setPlace_info(String place_info) {
        this.place_info = place_info;
    }
}
