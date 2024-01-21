package com.ra.repository;

import com.ra.model.entity.Address;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
//    @Query(value = "DELETE FROM address where id = ?1 and userId = ?2", nativeQuery = true)
    void deleteByUserAndId(User user, Long id);

    Page<Address> findAllByUser(User user, Pageable pageable);

    Address findByIdAndUser(Long id, User user);
}
