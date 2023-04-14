package training;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ProductDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class RestAssuredApiTests {

    @BeforeEach
    public void setBaseURI() {
        RestAssured.baseURI = "http://localhost:8888";
    }

    ProductDTO expectedProduct = ProductDTO.builder()
            .id(2)
            .name("Cross-Back Training Tank")
            .description("The most awesome phone of 2013!")
            .price(299.00)
            .categoryId(2)
            .categoryName("Active Wear - Women")
            .build();

    ProductDTO bottleProduct = ProductDTO.builder()
            .name("Water bottle")
            .description("Blue water bottle. Holds 64 ounces")
            .price(12.00)
            .categoryId(3)
            .build();


    @Test
    public void getCategories() {
        given()
                .when()
                .get("/api_testing/product/read.php")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void getProduct() {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("id", 2)
                .when()
                .get("/api_testing/product/read_one.php");

        int actualStatusCode = response.getStatusCode();
        String ActualHeader = response.getHeader("Content-Type");
        ProductDTO actualProduct = response.getBody().as(ProductDTO.class);

        assertEquals(HttpStatus.SC_OK, actualStatusCode, "Actual status code should be equals 200");
        assertEquals("application/json", ActualHeader, "Actual content-Type header should be equals - application/json");
        assertEquals(expectedProduct, actualProduct, "The resulting product should be equals the product model");

    }

    @Test
    public void createProduct() {
        given()
                .body(bottleProduct)
                .when()
                .post("/api_testing/product/create.php")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void getProducts() {
        Response response = given().get("/api_testing/product/read.php");
        int actualStatusCode = response.getStatusCode();
        String ActualHeader = response.getHeader("Content-Type");

        List<ProductDTO> actualProducts = response.body().jsonPath().getList("records", ProductDTO.class);

        assertEquals(HttpStatus.SC_OK, actualStatusCode, "Actual status code should be equals 200");
        assertEquals("application/json; charset=UTF-8", ActualHeader, "Actual content-Type header should be equals - application/json");
        assertNotNull(actualProducts);
    }

    @Test
    public void updateProduct() {
        ProductDTO bottle = new ProductDTO();
        bottle.setId(1000);
        bottle.setPrice(19.00);

        given()
                .body(bottle)
                .when()
                .contentType(ContentType.JSON)
                .put("/api_testing/product/update.php")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteProduct() {
        ProductDTO bottle = new ProductDTO();
        bottle.setId(1009);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(bottle)
                .delete("/api_testing/product/delete.php")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
