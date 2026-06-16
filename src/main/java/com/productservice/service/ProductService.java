package com.productservice.service;

import com.productservice.dto.request.ProductRequestDto;
import com.productservice.dto.request.ProductUpdateRequestDto;
import com.productservice.dto.response.CategoryCountDto;
import com.productservice.dto.response.CompanyCountDto;
import com.productservice.dto.response.ProductResponseDto;
import com.productservice.dto.response.StatusCountDto;
import com.productservice.enums.Category;
import com.productservice.enums.Status;

import java.util.List;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto requestDto);

    ProductResponseDto getById(String productId);

    List<ProductResponseDto> getAll();

    ProductResponseDto update(String productId, ProductUpdateRequestDto requestDto);

    void delete(String productId);

    Page<ProductResponseDto> getAllByPage(int page, int size, String sortBy);

    List<ProductResponseDto> getByCategory(Category category);

    List<ProductResponseDto> getByStatus(Status status);

    List<ProductResponseDto> getTopRatedProducts();

    List<ProductResponseDto> searchProducts(String keyword);

    List<CategoryCountDto> countProductsByCategory();

    List<CompanyCountDto> countProductsByCompany();

    List<StatusCountDto> countProductsByStatus();
}
