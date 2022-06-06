package com.example.CodeJavaApp.repository;

import com.example.CodeJavaApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPageRepository extends PagingAndSortingRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.lastname LIKE %?1%"
            + "OR u.email LIKE %?1%"
            + "OR u.address LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}
