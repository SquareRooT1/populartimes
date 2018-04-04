package response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.Reviews;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleMapResponse {

    private String formatedAddress;
    private String placeName;
    private String placeId;
    private List<String> types;
    private Map<String, Double> coordinates;
    private List<Reviews> reviews;

    @JsonProperty("result")
    private void mapPlaceIdentifier(Map<String, Object> result) {
        try {
            this.formatedAddress    = (String) result.getOrDefault("formatted_address", null);
            this.placeName          = (String) result.getOrDefault("name", null);
            this.placeId            = (String) result.getOrDefault("place_id", null);
            this.types              = (List<String>) result.getOrDefault("types",null);
            this.reviews            = (List<Reviews>) result.getOrDefault("reviews", null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public String getPlaceIdentifier(){
        return placeName + " " + formatedAddress;
    }

    public String getFormatedAddress() {
        return formatedAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }
}
