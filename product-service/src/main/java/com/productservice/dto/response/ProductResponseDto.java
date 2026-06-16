package com.productservice.dto.response;

import com.productservice.enums.Category;
import com.productservice.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductResponseDto {
    private String productId;
    private String name;
    private int maxRetailPrice;
    private byte discountPercentage;
    private int sellingPrice;
    private float rating;
    private int reviewsCount;
    private Category category;
    private String company;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
