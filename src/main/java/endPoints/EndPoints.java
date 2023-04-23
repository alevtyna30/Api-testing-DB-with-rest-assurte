package endPoints;

public interface EndPoints {
     static final String BASE_URL = "http://localhost:8888";
     static final String GET_ALL_CATEGORIES = BASE_URL + "/api_testing/product/read.php";
     static final String POST_CREATE_PRODUCT = BASE_URL + "/api_testing/product/create.php";
     static final String PUT_BY_PRODUCT = BASE_URL + "/api_testing/product/update.php";
     static final String DELETE_BY_PRODUCT = BASE_URL + "/api_testing/product/delete.php";
     static final String GET_BY_PRODUCT_QUERY_PARAM = BASE_URL + "/api_testing/product/read_one.php";
}
