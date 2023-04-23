package training;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.ProductDto;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static endPoints.EndPoints.*;


public class RestApiCalls {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

    }

    public void getCategories() {
        java.net.http.HttpRequest getCategories = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(GET_ALL_CATEGORIES))
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

    public void postProduct(ProductDto product, String endpoint) throws IOException, InterruptedException {

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

    public void putProduct(ProductDto productDto) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(productDto);
        String jsonRequest = objectMapper.writeValueAsString(productDto);

        java.net.http.HttpRequest putRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(PUT_BY_PRODUCT))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> putResponse = httpClient.send(putRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(putResponse.body());
    }


    public static void deleteProductByParameterAndValue(String param, String value) throws IOException, InterruptedException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(DELETE_BY_PRODUCT);
        uriBuilder.setParameter("id", "1011");

        java.net.http.HttpRequest deleteRequest = java.net.http.HttpRequest.newBuilder()
                .uri(uriBuilder.build())
                .DELETE()
                .build();

        HttpClient httpClientTwo = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> deleteResponse = httpClientTwo.send(deleteRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(deleteResponse.body());
        System.out.println(uriBuilder);
    }

    public void getProductResponseByParameterAndValue(String endpoint, String param, String value) {
        try {
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGetOneProduct = new HttpGet(endpoint);
            URI uri = new URIBuilder(httpGetOneProduct.getURI())
                    .addParameter(param, value)
                    .build();
            httpGetOneProduct.setURI(uri);

            org.apache.http.HttpResponse response = client.execute(httpGetOneProduct);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println(statusCode);
            System.out.println("response body" + responseBody);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
















