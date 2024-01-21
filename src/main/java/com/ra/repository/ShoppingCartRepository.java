package com.ra.repository;

import com.ra.model.entity.Shopping_Cart;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Shopping_Cart,Long> {
    Page<Shopping_Cart> findAllByUser(User user, Pageable pageable);

//    @Query(value = "SELECT sc.* FROM shopping_cart sc WHERE sc.id = ?1 and sc.userId = ?2", nativeQuery = true)
    Shopping_Cart findByIdAndUser(Integer id, User user);

    void deleteByUser(User user);
}
