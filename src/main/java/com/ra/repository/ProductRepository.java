package com.ra.repository;

import com.ra.model.dto.response.BestSellerProduct;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT p.* FROM Product p WHERE p.productName LIKE CONCAT('%', ?1, '%') OR p.description LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Product> findByProductNameOrDescription(String keyword);
    List<Product> findAllByCategory(Category category);
    @Query(value = "SELECT p.productName, SUM(od.orderQuantity) as orderQuantity " +
            "FROM product p " +
            "JOIN order_detail od ON p.id = od.productId " +
            "GROUP BY p.productName " +
            "ORDER BY orderQuantity DESC",
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM product p",
            nativeQuery = true)
    Page<Tuple> productBestSeller(Pageable pageable);

    default Page<BestSellerProduct> getBestSellerProducts(Pageable pageable) {
        Page<Tuple> tuplePage = productBestSeller(pageable);
        List<BestSellerProduct> result = mapTupleResultToDto(tuplePage.getContent());
        return new PageImpl<>(result, pageable, tuplePage.getTotalElements());
    }

    default List<BestSellerProduct> mapTupleResultToDto(List<Tuple> tupleList) {
        return tupleList.stream()
                .map(tuple -> new BestSellerProduct(
                        (String) tuple.get("productName"),
                        ((Number) tuple.get("orderQuantity")).intValue()
                ))
                .collect(Collectors.toList());
    }
}
