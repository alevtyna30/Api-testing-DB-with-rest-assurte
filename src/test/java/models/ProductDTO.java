package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class ProductDTO {
    public int id;
    public String name;
    public String description;
    public double price;
    @JsonProperty("category_id")
    public int categoryId;
    @JsonProperty("category_name")
    public String categoryName;

    public ProductDTO(String name, String description, double price, int categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}
