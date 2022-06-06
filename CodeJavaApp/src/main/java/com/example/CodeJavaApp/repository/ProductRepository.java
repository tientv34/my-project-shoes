package com.example.CodeJavaApp.repository;

import com.example.CodeJavaApp.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
        @Query("SELECT p FROM Products p WHERE p.name_prd LIKE %?1%"
                + "OR p.price LIKE %?1%"
                + "OR p.type LIKE %?1%")
        public List<Products> findByNameContaining(String name);

        @Query("SELECT p FROM Products p WHERE p.type = ?1")
        public List<Products> findByType(String type);

        @Query("SELECT p FROM Products p WHERE p.name_prd = ?1 AND p.type = ?2 ")
        List<Products> findByNameAndTypeProduct(String name_prd,String type);

        @Query("SELECT p FROM Products p WHERE p.name_prd = ?1 AND p.type = ?2 AND p.size = ?3 ")
        Products findByNameAndTypeProduct(String name_prd,String type,int size);

        @Query("SELECT p FROM Products p WHERE p.name_prd LIKE %?1%"
                + "OR p.price LIKE %?1%"
                + "OR p.type LIKE %?1%")
        Page<Products> findByTypeContaining(String name,Pageable pageable);

        Page<Products> findAll(Pageable pageable);

        @Query("SELECT p FROM Products p WHERE p.type = ?1")
         Page<Products> findByPageType(String type,Pageable pageable);

}
