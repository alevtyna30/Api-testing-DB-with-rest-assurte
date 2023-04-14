package training;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.ProductDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;


public class RestApiCalls {
    private static final String BASE_URL = "http://localhost:8888";
    private static final String GET_BY_CATEGORIES = BASE_URL + "/api_testing/product/read.php";
    private static final String POST_BY_PRODUCT = BASE_URL + "/api_testing/product/create.php";
    private static final String PUT_BY_PRODUCT = BASE_URL + "/api_testing/product/update.php";
    private static final String DELETE_BY_PRODUCT = BASE_URL + "/api_testing/product/delete.php";
    private static final String GET_BY_PRODUCT_ID = BASE_URL + "/api_testing/product/read_one.php";

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        deleteProduct();

    }

    public void getCategories() {
        java.net.http.HttpRequest getCategories = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(GET_BY_CATEGORIES))
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(getCategories, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
    }

    public void postProduct(ProductDTO product, String endpoint) throws IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(product);
        String jsonRequest = objectMapper.writeValueAsString(product);

        java.net.http.HttpRequest postRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> postResponse =
                httpClient.send(postRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());
    }

    public void putProduct() throws IOException, InterruptedException {
        ProductDTO bottle = new ProductDTO();
        bottle.setId(1000);
        bottle.setPrice(80.00);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(bottle);
        String jsonRequest = objectMapper.writeValueAsString(bottle);

        java.net.http.HttpRequest putRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(PUT_BY_PRODUCT))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> putResponse = httpClient.send(putRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(putResponse.body());
    }


    public static void deleteProduct() throws IOException, InterruptedException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(DELETE_BY_PRODUCT);
        uriBuilder.setParameter("id", "1011");

        java.net.http.HttpRequest deleteRequest = java.net.http.HttpRequest.newBuilder()
                .uri(uriBuilder.build())
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpClient httpClientTwo = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> deleteResponse = httpClientTwo.send(deleteRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(deleteResponse.body());
        System.out.println(uriBuilder);
    }

    public void getProductById(String endpoint, String param, String value) {
        try {
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGetOneProduct = new HttpGet(endpoint);
            URI uri = new URIBuilder(httpGetOneProduct.getURI())
                    .addParameter(param, value)
                    .build();
            httpGetOneProduct.setURI(uri);

            HttpResponse response = client.execute(httpGetOneProduct);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println(statusCode);
            System.out.println("response body" + responseBody);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

















