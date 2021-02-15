import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.netology.model.Post;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final String URL = "http://localhost:8080/api/posts";
    private static final String URL_CURRENT_ID = "http://localhost:8080/api/posts/1";

    public static void main(String[] args) throws IOException {
        var httpClient = HttpClientBuilder.create().build();
        // GET
//        var getRequest = new HttpGet(URL);
//        getRequest.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
//        var response = httpClient.execute(getRequest);
//        System.out.println(new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8));

        // DELETE
//        var deleteRequest = new HttpDelete(URL_CURRENT_ID);
//        var deleteResponse = httpClient.execute(deleteRequest);
//        System.out.println(new String(deleteResponse.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8));

        // POST
        var postRequest = new HttpPost(URL);
        postRequest.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        var post = new Post(0L, "Hello World!!!");
        var builder = new GsonBuilder();
        var gson = builder.create();
        var json = gson.toJson(post);
        var entity = new StringEntity(json);
        postRequest.setEntity(entity);
        var postResponse = httpClient.execute(postRequest);
        System.out.println(new String(postResponse.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8));
    }
}
