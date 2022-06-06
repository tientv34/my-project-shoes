package com.example.CodeJavaApp.service.impl;

import com.example.CodeJavaApp.entity.BillDetail;
import com.example.CodeJavaApp.entity.Bills;
import com.example.CodeJavaApp.repository.BillDetailRepository;
import com.example.CodeJavaApp.repository.BillsRepository;
import com.example.CodeJavaApp.service.IBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IBillsServiceImpl implements IBillsService {

    @Autowired private BillsRepository billsRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;


    @Override
    public void save(Bills bills) {
        billsRepository.save(bills);
    }

    @Override
    public void addBillDetail(BillDetail billDetail) {
            billDetailRepository.save(billDetail);
    }

    @Override
    public Optional<Bills> findBillById(Long id) {
        return billsRepository.findById(id);
    }

    @Override
    public int AddBills(Bills bills) {
        billsRepository.save(bills);
        return 1;
    }

    @Override
    public List<Bills> getAllBill() {
        return billsRepository.findAll();
    }

    @Override
    public List<BillDetail> findBillDetailByIdHd(Long id) {
        return billDetailRepository.findByIdHd(id);
    }

    @Override
    public Optional<BillDetail> findBillDetailId(Long id) {
        return billDetailRepository.findById(id);
    }


    @Override
    public Page<Bills> listAllBillsPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1,6);
        return billsRepository.findAll(pageable);
    }

    @Override
    public List<Bills> findByIduser(Long iduser) {
        return billsRepository.findByIduser(iduser);
    }
}
