package util;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import util.GlobalUtil;

import java.io.InputStream;

public class HttpRequest<T> {

    public T request(String url, Class<T> clazz) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            InputStream inputStream = httpResponse.getEntity().getContent();
            return GlobalUtil.MAPPER.readValue(inputStream, clazz);
        } catch (Exception e) {
            //TODO add loger
            System.out.println(e.getMessage());
        }

        return null;
    }

}
