package com.ra.repository;

import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import com.ra.model.entity.User;
import org.hibernate.query.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Page<Orders> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT o.* from orders o where serialNumber LIKE CONCAT('%', ?1, '%') and userId = ?2", nativeQuery = true)
    List<Orders> findAllBySerialNumberContainingIgnoreCase(String serialNumber,Long userId);
    @Query(value = "SELECT o.* from orders o where status LIKE CONCAT('%', ?1, '%') and userId = ?2", nativeQuery = true)
    List<Orders> findAllByStatusOrdersContainingIgnoreCase(String status,Long userId);
}
