package training;


import models.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetProductTest {
    private static final double EXPECTED_PRODUCT_PRICE = 68.0;
    private static final double EXPECTED_AVERAGE_PRICE = 61.0;
    private static final String EXPECTED_PRODUCT_NAME = "Stretchy Dance Pants";
    private static RestAssuredApiHelper helper;

    @BeforeEach
    public void setup() {
        helper = new RestAssuredApiHelper();
    }

    @Test
    public void getProductsByCategoryNameAndId() {
        List<ProductDto> actualProducts = helper.getAllProducts();

        List<ProductDto> filteredProducts = actualProducts.stream()
                .filter(
                        c -> c.getCategoryName().equals("Active Wear - Women"))
                .collect(Collectors.toList());

        Assertions.assertTrue(filteredProducts.stream().anyMatch(c -> c.getName().equals(EXPECTED_PRODUCT_NAME)),
                "Check the actual product it doesn't equal to expected");
        Assertions.assertEquals(EXPECTED_PRODUCT_PRICE, actualProducts.stream()
                .filter(c -> c.getId() == 3)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product with id 3 not found"))
                .getPrice(), "Check the actual price it doesn't equal to expected");
        Assertions.assertTrue(actualProducts.stream()
                .filter(c -> c.getId() == 1)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product with id 1 not found"))
                .getPrice() > 90.0, "The price lower than 90.0");

    }

    @Test
    public void getAveragePriceFromTheProducts() {
        List<ProductDto> actualProducts = helper.getAllProducts();
        double actualValue = calculateAveragePriceOfTheProduct(actualProducts);

        Assertions.assertEquals(EXPECTED_AVERAGE_PRICE, actualValue,"The actual value doesn't equal to expected");
    }

    @Test
    public void getIdAndNameFromResponseToMap() {
        List<ProductDto> actualProducts = helper.getAllProducts();

        Map<Integer, String> actualMap = getIdAndNameFromResponseToMap(actualProducts);

        Map<Integer, String> expectedMap = new HashMap<>();
        expectedMap.put(1, "Bamboo Thermal Ski Coat");
        expectedMap.put(2, "Cross-Back Training Tank");
        expectedMap.put(3, "Grunge Skater Jeans");
        expectedMap.put(4, "Slicker Jacket");
        expectedMap.put(5, "Stretchy Dance Pants");
        expectedMap.put(6, "UItra-Soft Tank Top");
        expectedMap.put(7, "Unisex Thermal Vest");
        expectedMap.put(8, "V-Neck T-Shirt");
        expectedMap.put(9, "Polo Shirt");
        expectedMap.put(10, "Skater Graphic T-Shirt");
        expectedMap.put(11, "Thermal Fleece Jacket");
        expectedMap.put(12, "V-Neck Pullover");
        expectedMap.put(13, "V-Neck Sweater");
        expectedMap.put(14, "Calcium 400 IU (150 tablets)");
        expectedMap.put(15, "Flaxseed Oil 1000 mg (90 capsule");
        expectedMap.put(16, "Iron 65 mg (150 caplets)");
        expectedMap.put(17, "Magnesium 250 mg (100 tablets)");
        expectedMap.put(18, "Multi-Vitamin (90 capsules)");

        Assertions.assertEquals(actualMap, expectedMap, "The actual map doesn't equal to expected");
    }

    public double calculateAveragePriceOfTheProduct(List<ProductDto> productsDto) {
        return productsDto.stream()
                .mapToDouble(ProductDto::getPrice)
                .average()
                .stream()
                .map(Math::round)
                .findFirst()
                .orElse(0.0);
    }

    public Map<Integer, String> getIdAndNameFromResponseToMap(List<ProductDto> productsDto) {
        return productsDto.stream()
                .collect(Collectors.toMap(ProductDto::getId, ProductDto::getName));
    }
}
