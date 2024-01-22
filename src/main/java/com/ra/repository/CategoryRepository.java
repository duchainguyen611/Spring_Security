package com.ra.repository;

import com.ra.model.dto.response.TotalPriceByCategory;
import com.ra.model.entity.Category;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT c.categoryName, SUM(od.unitPrice) as totalPrice " +
            "FROM category c " +
            "JOIN product p ON c.id = p.categoryId " +
            "JOIN order_detail od ON p.id = od.productId " +
            "GROUP BY c.categoryName", nativeQuery = true)
    List<Tuple> totalPriceByCategory();

    default List<TotalPriceByCategory> getTotalPriceByCategory() {
        List<Tuple> tupleList = totalPriceByCategory();
        return mapTupleResultToDto(tupleList);
    }

    default List<TotalPriceByCategory> mapTupleResultToDto(List<Tuple> tupleList) {
        return tupleList.stream()
                .map(tuple -> new TotalPriceByCategory(
                        (String) tuple.get("categoryName"),
                        ((Number) tuple.get("totalPrice")).doubleValue()
                ))
                .collect(Collectors.toList());
    }
}
