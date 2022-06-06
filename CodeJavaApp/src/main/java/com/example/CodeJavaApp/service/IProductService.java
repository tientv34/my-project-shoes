package com.example.CodeJavaApp.service;

import com.example.CodeJavaApp.entity.Bills;
import com.example.CodeJavaApp.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Products> getAllPrd();

    Products savePrd(Products prd);

    void save(Products prd);

    void deletePrd(Long id);

    List<Products> findByNameContaining(String name_prd);

    List<Products> findByTypeContaining(String type);

    Optional<Products> findPrdById(Long id);

    Page<Products> listAllProductPage(int pageNumber,String keyword);

    Page<Products> listAllBillsPage(String type,int pageNumber);

    Page<Products> findByTypePage(String type,int pageNumber,String sortField, String sortDir);
}
