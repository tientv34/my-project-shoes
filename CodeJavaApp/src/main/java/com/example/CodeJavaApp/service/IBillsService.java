package com.example.CodeJavaApp.service;

import com.example.CodeJavaApp.entity.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IBillsService {
    void save(Bills bills);

    void addBillDetail(BillDetail billDetail);

    Optional<Bills> findBillById(Long id);

    int AddBills(Bills bills);

    List<Bills> getAllBill();

    List<BillDetail> findBillDetailByIdHd(Long id);

    Optional<BillDetail> findBillDetailId(Long id);

    Page<Bills> listAllBillsPage(int pageNumber);

    List<Bills> findByIduser(Long iduser);
}
