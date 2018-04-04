import populartimes.PopularTimes;

import java.net.URISyntaxException;

public class Demo {

    public static  void main(String []args) throws URISyntaxException {
        PopularTimes popularTimes = new PopularTimes();
        System.out.println(popularTimes.getPlace("ChIJC9bMfV24yhQRGG2vjgDI4oQ")); // Give google placeid
    }
}
