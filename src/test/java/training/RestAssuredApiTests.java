package training;


import io.restassured.response.Response;
import models.ProductDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


public class RestAssuredApiTests {

    private static RestAssuredApiHelper helper;

    @BeforeEach
    public void setup() {
        helper = new RestAssuredApiHelper();
    }

    @Test
    public void getAllCategoriesTest() {
        Response response = helper.getAllCategories();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    public void getProductByQueryParamTest() {
        Response response = helper.getProductByQueryParam("id", 2);

        assertEquals(HttpStatus.SC_OK, response.statusCode(), "Actual status code should be equals 200");
        assertEquals("application/json", response.getHeader("Content-Type"), "Actual content-Type header should be equals - application/json");
        assertEquals(ProductDto.builder().id(2).name("Cross-Back Training Tank").description("The most awesome phone of 2013!").price(299.00).categoryId(2).
                categoryName("Active Wear - Women").build(),
                response.body().as(ProductDto.class), "The resulting product should be equals the product model");
    }

    @Test
    public void createProductTest() {
        Response response = helper.createProduct(ProductDto.builder().name("Water bottle").
                description("Blue water bottle. Holds 64 ounces").price(14.00).categoryId(3).build());

        assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
    }

    @Test
    public void getAllProductsInListTest() {
        List<ProductDto> actualProducts = helper.getAllProducts();

        assertNotNull(actualProducts);
    }

    @Test
    public void updateProductTest() {
        Response response = helper.updateProduct(ProductDto.builder().id(1011).price(89.00).build());

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    public void deleteProductTest() {
        Response response = helper.deleteProduct(ProductDto.builder().id(1011).build());

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

    }
}
