package com.example.CodeJavaApp.repository;

import com.example.CodeJavaApp.entity.BillDetail;
import com.example.CodeJavaApp.entity.Bills;
import com.example.CodeJavaApp.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillsRepository extends JpaRepository<Bills, Long> {
    @Query(value = "SELECT max(id) FROM Bills")
    public Long max();
    Page<Bills> findAll(Pageable pageable);

    @Query(value = "SELECT b FROM Bills b WHERE b.user.id = ?1")
    public List<Bills> findByIduser(Long id);
}

