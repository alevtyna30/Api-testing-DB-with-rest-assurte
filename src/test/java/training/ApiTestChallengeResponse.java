package training;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ProductDTO;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApiTestChallengeResponse {

    public final static String GET_ONE_PRODUCT = "http://localhost:8888/api_testing/product/read_one.php";
    ProductDTO product = ProductDTO.builder()
            .id(18)
            .name("Multi-Vitamin (90 capsules)")
            .description("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.")
            .price(10.00)
            .categoryId(4)
            .categoryName("Supplements")
            .build();

    @Test
    public void getMultiVitamins() {
        ProductDTO actual = given().
                contentType(ContentType.JSON)
                .queryParam("id", 18).
                when().
                get(GET_ONE_PRODUCT).
                then().
                extract().as(ProductDTO.class);

        Response response = given().get(GET_ONE_PRODUCT);
        int statusCode = response.getStatusCode();
        String header = response.getHeader("Content-Type");


        assertEquals("application/json", header);
        assertEquals(200, statusCode, "Status code should be 200");
        assertEquals(product, actual, "The resulting product should be equals the product model");

    }
}
