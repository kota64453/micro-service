package com.productservice.service.impl;

import com.productservice.dto.request.ProductRequestDto;
import com.productservice.dto.request.ProductUpdateRequestDto;
import com.productservice.dto.response.ProductResponseDto;
import com.productservice.dto.response.StatusCountDto;
import com.productservice.entity.Product;
import com.productservice.enums.Category;
import com.productservice.enums.Status;
import com.productservice.exception.ProductNotFoundException;
import com.productservice.repository.ProductRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto save(
            ProductRequestDto requestDto) {

        Product product =
                modelMapper.map(
                        requestDto,
                        Product.class
                );

        product.setRating(0F);
        product.setReviewsCount(0);
        product.setCreatedDate(LocalDateTime.now());
        product.setUpdatedDate(null);

        Product savedProduct =
                productRepository.save(product);

        return mapToResponseDto(savedProduct);
    }
    @Override
    public ProductResponseDto getById(
            String productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        return mapToResponseDto(product);
    }
    @Override
    public List<ProductResponseDto> getAll() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public ProductResponseDto update(
            String productId,
            ProductUpdateRequestDto productUpdateRequestDto) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        modelMapper.map(productUpdateRequestDto, product);

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponseDto(updatedProduct);
    }
    @Override
    public void delete(String productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        productRepository.delete(product);
    }
    @Override
    public Page<ProductResponseDto> getAllByPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).descending()
                );

        Page<Product> productPage =
                productRepository.findAll(pageable);

        return productPage.map(this::mapToResponseDto);
    }
    @Override
    public List<ProductResponseDto> getByCategory(
            Category category) {

        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<ProductResponseDto> getByStatus(
            Status status) {

        return productRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<ProductResponseDto> getTopRatedProducts() {

        return productRepository
                .findTop10ByOrderByRatingDesc()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<ProductResponseDto> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<CategoryCountDto> countProductsByCategory() {

        return productRepository.countProductsByCategory()
                .stream()
                .map(response -> CategoryCountDto.builder()
                        .category(response.getCategory())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }
    @Override
    public List<CompanyCountDto> countProductsByCompany() {

        return productRepository.countProductsByCompany()
                .stream()
                .map(response -> CompanyCountDto.builder()
                        .company(response.getCompany())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }
    @Override
    public List<StatusCountDto> countProductsByStatus() {

        return productRepository.countProductsByStatus()
                .stream()
                .map(response -> StatusCountDto.builder()
                        .status(response.getStatus())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }
    private ProductResponseDto mapToResponseDto(
            Product product) {

        ProductResponseDto responseDto =
                modelMapper.map(
                        product,
                        ProductResponseDto.class
                );

        int discountAmount =
                product.getMaxRetailPrice()
                        * product.getDiscountPercentage()
                        / 100;

        int sellingPrice =
                product.getMaxRetailPrice()
                        - discountAmount;

        responseDto.setSellingPrice(sellingPrice);

        return responseDto;
    }
}
