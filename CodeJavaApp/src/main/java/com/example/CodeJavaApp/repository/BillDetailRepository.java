package com.example.CodeJavaApp.repository;

import com.example.CodeJavaApp.entity.BillDetail;
import com.example.CodeJavaApp.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail,Long> {
    @Query(value = "SELECT b FROM BillDetail b WHERE b.bills.id = ?1")
    public List<BillDetail> findByIdHd(Long id);

//    @Query("SELECT p FROM Products p WHERE p.type = ?1")
//    public List<Products> findByTypeContaining(String type);
}
