package training;


import io.restassured.response.Response;
import models.ProductDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApiTestChallengeResponse {
    @Test
    public void getMultiVitaminsTest() {
        RestAssuredApiHelper helper = new RestAssuredApiHelper();
        Response response = helper.getProductByQueryParam("id", 18);

        assertEquals(HttpStatus.SC_OK, response.statusCode(), "Actual status code should be equals 200");
        assertEquals("application/json", response.getHeader("Content-Type"), "Actual content-Type header should be equals - application/json");
        assertEquals(ProductDto.builder().id(18).name("Multi-Vitamin (90 capsules)").description("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.").
                        price(10.00).categoryId(4).categoryName("Supplements").build(),
                response.body().as(ProductDto.class), "The resulting product should be equals the product model");
    }
}
