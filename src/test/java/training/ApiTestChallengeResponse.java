package training;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ProductDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApiTestChallengeResponse {

    public final static String GET_ONE_PRODUCT = "http://localhost:8888/api_testing/product/read_one.php";
    ProductDTO expectedProduct = ProductDTO.builder()
            .id(18)
            .name("Multi-Vitamin (90 capsules)")
            .description("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.")
            .price(10.00)
            .categoryId(4)
            .categoryName("Supplements")
            .build();

    @Test
    public void getMultiVitamins() {
        Response response = given().
                contentType(ContentType.JSON).
                queryParam("id", 18).
                when().
                get(GET_ONE_PRODUCT);

        int actualStatusCode = response.getStatusCode();
        String ActualHeader = response.getHeader("Content-Type");
        ProductDTO actualProduct = response.getBody().as(ProductDTO.class);

        assertEquals(HttpStatus.SC_OK, actualStatusCode, "Actual status code should be equals 200");
        assertEquals("application/json", ActualHeader, "Actual content-Type header should be equals - application/json");
        assertEquals(expectedProduct, actualProduct, "The resulting product should be equals the product model");

    }
}
