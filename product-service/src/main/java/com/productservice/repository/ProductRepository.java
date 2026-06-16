package com.productservice.repository;

import com.productservice.entity.Product;
import com.productservice.enums.Category;
import com.productservice.enums.Status;
import com.productservice.projection.CategoryCountResponse;
import com.productservice.projection.CompanyCountResponse;
import com.productservice.projection.StatusCountResponse;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findByName(String name);

    List<Product> findByCompany(String company);

    List<Product> findByCategory(Category category);

    List<Product> findByStatus(Status status);
    List<Product> findByMaxRetailPriceBetween(
            int minPrice,
            int maxPrice);

    List<Product> findByMaxRetailPriceLessThan(int price);

    List<Product> findByMaxRetailPriceGreaterThan(int price);

    List<Product> findByDiscountPercentageGreaterThan(byte discountPercentage);
    List<Product> findTop10ByOrderByRatingDesc();

    List<Product> findTop5ByCategoryOrderByRatingDesc(
            Category category);

    List<Product> findTop10ByStatusOrderByRatingDesc(
            Status status);
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCompanyContainingIgnoreCase(
            String keyword);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(
            Status category,
            Pageable pageable);
    Page<Product> findByStatus(
            Status status,
            Pageable pageable);

    Page<Product> findByCompany(
            String company,
            Pageable pageable);

    Page<Product> findByBrand(
            String brand,
            Pageable pageable);
    List<Product> findAllByOrderByMaxRetailPriceAsc();

    List<Product> findAllByOrderByMaxRetailPriceDesc();

    List<Product> findAllByOrderByRatingDesc();

    List<Product> findAllByOrderByCreatedDateDesc();
    long countByCategory(Category category);

    long countByStatus(Status status);

    long countByCompany(String company);
    @Aggregation(pipeline = {
            """
            {
                $group: {
                    _id: "$category",
                    totalProducts: { $sum: 1 }
                }
            }
            """,
            """
            {
                $project: {
                    category: "$_id",
                    totalProducts: 1,
                    _id: 0
                }
            }
            """
    })
    List<CategoryCountResponse> countProductsByCategory();

    @Aggregaation (pipeline = {
            """
            {
                $group: {
                    _id: "$company",
                    totalProducts: { $sum: 1 }
                }
            }
            """,
            """
            {
                $project: {
                    company: "$_id",
                    totalProducts: 1,
                    _id: 0
                }
            }
            """
    })
    List<CompanyCountResponse> countProductsByCompany();

    @Aggregation(pipeline = {
            """
            {
                $group: {
                    _id: "$status",
                    totalProducts: { $sum: 1 }
                }
            }
            """,
            """
            {
                $project: {
                    status: "$_id",
                    totalProducts: 1,
                    _id: 0
                }
            }
            """
    })
    List<StatusCountResponse> countProductsByStatus();
    List<Product> findByRatingGreaterThanEqual(Float rating);
    List<Product> findByReviewsCountGreaterThan(Integer count);
    List<Product> findTop10ByOrderByCreatedDateDesc();
    List<Product> findByCategoryAndStatus(
            Category category,
            Status status);
    List<Product> findByCategoryAndMaxRetailPriceBetween(
            Category category,
            int minPrice,
            int maxPrice);
    List<Product> findByCategoryAndRatingGreaterThanEqual(
            Category category,
            float rating);

    <T> ScopedValue<T> findById(String productId);

    void delete(Product product);

    Product save(Product product);
}
