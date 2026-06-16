package com.productservice.controller;

import com.productservice.dto.request.ProductRequestDto;
import com.productservice.dto.request.ProductUpdateRequestDto;
import com.productservice.dto.response.CategoryCountDto;
import com.productservice.dto.response.CompanyCountDto;
import com.productservice.dto.response.ProductResponseDto;
import com.productservice.dto.response.StatusCountDto;
import com.productservice.enums.Category;
import com.productservice.enums.Status;
import com.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {
   // @GetMapping
   // public ResponseEntity<String> getProducts(){
      //  return ResponseEntity.ok("Product Data");
//    }
    private final ProductService ProductService;

    @PostMapping
    public ResponseEntity<ProductResponseDto>save(
            @RequestBody ProductRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductService.save(requestDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable String productId) {
        return ResponseEntity.ok(ProductService.getById(productId));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok(ProductService.getAll());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable String productId,
            @RequestBody ProductUpdateRequestDto requestDto) {

        return ResponseEntity.ok(ProductService.update(
                productId,
                requestDto
        ));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable String productId) {
        ProductService.delete(productId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/pagination")
    public ResponseEntity<Page<ProductResponseDto>> getAllByPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {

        return ResponseEntity.ok(ProductService.getAllByPage(
                page,
                size,
                sortBy
        ));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
            @PathVariable Category category) {
        return ResponseEntity.ok(ProductService.getByCategory(category));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByStatus(
            @PathVariable Status status) {

        return ResponseEntity.ok(ProductService.getByStatus(status));
    }
    @GetMapping("/top-rated")
    public ResponseEntity<List<ProductResponseDto>> getTopRatedProducts() {
        return ResponseEntity.ok(ProductService.getTopRatedProducts());
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(ProductService.searchProducts(keyword));
    }
    @GetMapping("/count-by-category")
    public ResponseEntity<List<CategoryCountDto>> countProductsByCategory() {
        return ResponseEntity.ok(ProductService.countProductsByCategory());
    }
    @GetMapping("/count-by-company")
    public ResponseEntity<List<CompanyCountDto>> countProductsByCompany() {
        return ResponseEntity.ok(ProductService.countProductsByCompany());
    }
    @GetMapping("/count-by-status")
    public ResponseEntity<List<StatusCountDto>> countProductsByStatus() {

        return ResponseEntity.ok(ProductService.countProductsByStatus());
    }
}
