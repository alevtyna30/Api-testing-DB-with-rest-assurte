package training;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ProductDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class ApiTests {

    @BeforeEach
    public void setBaseURI() {
        RestAssured.baseURI = "http://localhost:8888";
    }

    ProductDTO tankProduct = ProductDTO.builder()
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
        ProductDTO productActual = given()
                .contentType(ContentType.JSON)
                .queryParam("id", 2)
                .when()
                .get("/api_testing/product/read_one.php")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(ProductDTO.class);

        assertEquals(tankProduct, productActual);

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
        List<ProductDTO> products = given()
                .get("/api_testing/product/read.php")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .header("Content-Type", "application/json; charset=UTF-8")
                .extract()
                .body()
                .jsonPath().getList("records", ProductDTO.class);

        assertNotNull(products);
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
        bottle.setId(1000);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(bottle)
                .delete("/api_testing/product/delete.php")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
