package com.ra.repository;

import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);
    @Query(value = "SELECT u.* FROM User u join user_role ur on u.id=ur.user_id WHERE ur.role_id = 2", nativeQuery = true)
    Page<User> findAllByUser(Pageable pageable);

    Page<User> findAllByUserNameContainingIgnoreCase(String keyWord,Pageable pageable);
}
