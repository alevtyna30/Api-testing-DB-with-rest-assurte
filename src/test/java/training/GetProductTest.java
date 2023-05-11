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
                        c -> c.getCategoryName().equals("Active Wear - Women") || c.getId() == 3 || c.getId() == 1)
                .collect(Collectors.toList());

        Assertions.assertTrue(filteredProducts.stream().anyMatch(c -> c.getName().equals("Stretchy Dance Pants")));
        Assertions.assertEquals(68.0, filteredProducts.stream()
                .filter(c -> c.getId() == 3)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product with id 3 not found"))
                .getPrice());
        Assertions.assertTrue(filteredProducts.stream()
                .filter(c -> c.getId() == 1)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product with id 1 not found"))
                .getPrice() > 90.0);

    }

    @Test
    public void getAveragePriceFromTheProducts() {
        List<ProductDto> actualProducts = helper.getAllProducts();
        double actualValue = calculateAveragePriceOfTheProduct(actualProducts);

        Assertions.assertEquals(60.888888888888886, actualValue);
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

        Assertions.assertEquals(actualMap, expectedMap);
    }

    public double calculateAveragePriceOfTheProduct(List<ProductDto> productsDto) {
        return productsDto.stream()
                .mapToDouble(ProductDto::getPrice)
                .average()
                .orElse(0.0);
    }

    public Map<Integer, String> getIdAndNameFromResponseToMap(List<ProductDto> productsDto) {
        return productsDto.stream()
                .collect(Collectors.toMap(ProductDto::getId, ProductDto::getName));
    }
}
