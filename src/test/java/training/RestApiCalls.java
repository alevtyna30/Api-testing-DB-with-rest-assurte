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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;


public class RestApiCalls {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

    }

    public void getCategories() {
        java.net.http.HttpRequest getCategories = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api_testing/product/read.php"))
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

    public void createProduct() throws IOException, InterruptedException {
        ProductDTO bottleProduct = ProductDTO.builder()
                .name("Water bottle")
                .description("Blue water bottle. Holds 64 ounces")
                .price(12.00)
                .categoryId(3)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(bottleProduct);
        String jsonRequest = objectMapper.writeValueAsString(bottleProduct);

        java.net.http.HttpRequest postRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api_testing/product/create.php"))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> postResponse =
                httpClient.send(postRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());
    }

    public void updateProduct() throws IOException, InterruptedException {
        ProductDTO bottle = new ProductDTO();
        bottle.setId(1000);
        bottle.setPrice(80.00);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(bottle);
        String jsonRequest = objectMapper.writeValueAsString(bottle);

        java.net.http.HttpRequest putRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api_testing/product/update.php"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> putResponse = httpClient.send(putRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(putResponse.body());
    }


    public void deleteProduct() throws IOException, InterruptedException {
        int idToDelete = 1004;
        java.net.http.HttpRequest deleteRequest = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api_testing/product/delete.php/" + idToDelete))
                .DELETE()
                .build();

        HttpClient httpClientTwo = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> deleteResponse = httpClientTwo.send(deleteRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(deleteResponse.body());
    }

    public void getProductById() throws URISyntaxException, IOException {
        HttpGet httpGetOneProduct = new HttpGet("http://localhost:8888/api_testing/product/read_one.php");
        URI uri = new URIBuilder(httpGetOneProduct.getURI())
                .addParameter("id", "18")
                .build();
        httpGetOneProduct.setURI(uri);
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();

        HttpResponse response = client.execute(httpGetOneProduct);

        int statusCode = response.getStatusLine().getStatusCode();

        System.out.println(statusCode);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
    }
}
















