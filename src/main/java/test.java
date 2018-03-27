import populartimes.PopularTimes;

import java.net.URISyntaxException;

public class test {

    public static  void main(String []args) throws URISyntaxException {
        PopularTimes popularTimes = new PopularTimes();
        System.out.println(popularTimes.getPlace("")); // Give google placeid
    }
}
