package training;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiTestsChallengeRequest {

    @Test

    public void createProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        String body = "{\"name\": \"Sweatband\", \"description\": \"95% recycled, light and comfortable\", \"price\": 5, \"category_id\":3}";
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = "{\"id\":22,\"price\": 6}";
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void getSweatband(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        var response = given().queryParam("id", 22).when().get(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = "{\"id\":23}";
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

}
