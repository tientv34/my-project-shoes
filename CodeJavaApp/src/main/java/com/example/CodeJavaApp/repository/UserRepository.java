package com.example.CodeJavaApp.repository;

import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.lastname LIKE %?1%"
            + "OR u.email LIKE %?1%"
            + "OR u.address LIKE %?1%")
     List<User> findByNameContaining(String name);

     User findByResetPasswordToken(String token);
}
