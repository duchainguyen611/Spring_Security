package com.ra.repository;

import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import com.ra.model.entity.User;
import org.hibernate.query.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Page<Orders> findAllByUser(User user, Pageable pageable);

    List<Orders> findAllBySerialNumberContainingIgnoreCase(String serialNumber);
}
