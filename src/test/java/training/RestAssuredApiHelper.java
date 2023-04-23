package training;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ProductDto;

import java.util.List;

import static endPoints.EndPoints.*;
import static io.restassured.RestAssured.given;


public class RestAssuredApiHelper {

    public Response getProductByQueryParam(String id, int value) {
        Response response = given().
                queryParam(id, value).
                when().
                get(GET_BY_PRODUCT_QUERY_PARAM).
                then().extract().response();
        return response;
    }

    public Response createProduct(ProductDto product) {
        Response response = given().
                body(product).
                contentType(ContentType.JSON).
                when().
                post(POST_CREATE_PRODUCT).
                then().
                extract().response();
        return response;
    }

    public Response getAllCategories() {
        Response response = given().
                when().
                get(GET_ALL_CATEGORIES).
                then().
                extract().response();
        return response;
    }

    public Response deleteProduct(ProductDto productDto) {
        Response response = given().
                when().
                delete(DELETE_BY_PRODUCT).
                then().
                extract().response();
        return response;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductDto> actualProducts = given().
                get(GET_ALL_CATEGORIES).
                getBody().jsonPath().getList("records", ProductDto.class);

        return actualProducts;
    }

    public Response updateProduct(ProductDto productDto) {
        Response response = given().
                body(productDto).
                when().
                contentType(ContentType.JSON).
                put(PUT_BY_PRODUCT).
                then().extract().response();

        return response;
    }

}
