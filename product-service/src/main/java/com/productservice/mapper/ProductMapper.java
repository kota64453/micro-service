package com.productservice.mapper;

import com.productservice.dto.response.ProductResponseDto;
import com.productservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductResponseDto mapToResponseDto(
            Product product) {

        ProductResponseDto responseDto =
                modelMapper.map(
                        product,
                        ProductResponseDto.class
                );

        int sellingPrice =
                product.getMaxRetailPrice()
                        - (
                        product.getMaxRetailPrice()
                                * product.getDiscountPercentage()
                                / 100
                );

        responseDto.setSellingPrice(sellingPrice);

        return responseDto;
    }
}
