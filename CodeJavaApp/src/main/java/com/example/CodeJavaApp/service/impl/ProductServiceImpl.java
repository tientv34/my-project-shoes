package com.example.CodeJavaApp.service.impl;

import com.example.CodeJavaApp.entity.Bills;
import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.repository.ProductRepository;
import com.example.CodeJavaApp.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
   ProductRepository prdRepository;

    public ProductServiceImpl() {
    }

    @Override
    public List<Products> getAllPrd() {
        return (List<Products>) prdRepository.findAll();
    }

    @Override
    public Products savePrd(Products prd) {
        return new Products();
    }

    @Override
    public void save(Products prd) {
        prdRepository.save(prd);
    }

    @Override
    public void deletePrd(Long id_prd) {
        prdRepository.deleteById(id_prd);
    }

    @Override
    public List<Products> findByNameContaining(String name_prd) {
        if(name_prd != null){
              return prdRepository.findByNameContaining(name_prd);
        }
        return prdRepository.findAll();
    }

    @Override
    public List<Products> findByTypeContaining(String type) {
        if(type != null){
            return prdRepository.findByType(type);
        }
        return prdRepository.findAll();
    }

    @Override
    public Optional<Products> findPrdById(Long id) {
        return prdRepository.findById(id);
    }

    @Override
    public Page<Products> listAllProductPage(int pageNumber,String keyword) {
        Pageable pageable = PageRequest.of(pageNumber-1,8);
        if (keyword != null){
          return  prdRepository.findByTypeContaining(keyword,pageable);
        }
        return prdRepository.findAll(pageable);
    }

    @Override
    public Page<Products> listAllBillsPage(String type,int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1,4);
        return prdRepository.findByPageType(type,pageable);
    }

    @Override
    public Page<Products> findByTypePage(String type, int pageNumber,String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
             sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
            Pageable pageable = PageRequest.of(pageNumber-1,6,sort);
        return prdRepository.findByPageType(type,pageable);
    }


}
