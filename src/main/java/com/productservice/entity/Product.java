package com.productservice.entity;

import com.productservice.enums.Category;
import com.productservice.enums.Status;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private int maxRetailPrice;
    private byte discountPercentage;
    private float rating;
    private int reviewsCount;
    private Category category;
    private String company;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
