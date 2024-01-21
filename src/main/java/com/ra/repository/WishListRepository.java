package com.ra.repository;

import com.ra.model.entity.User;
import com.ra.model.entity.Wish_List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<Wish_List,Long> {
    Page<Wish_List> findAllByUser(User user, Pageable pageable);
    void deleteByUserAndId(User user, Long id);

    Wish_List findByIdAndUser(Long id, User user);
}
