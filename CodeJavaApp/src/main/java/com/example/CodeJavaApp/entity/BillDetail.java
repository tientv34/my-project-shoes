package com.example.CodeJavaApp.entity;

import javax.persistence.*;

@Entity
@Table(name = "billdetails")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hd")
    private Bills bills;

    @ManyToOne
    @JoinColumn(name = "id_prd")
    private Products products;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;

    public BillDetail() {
    }

    public BillDetail(Long id, Bills bills, Products products, int quantity, double price) {
        this.id = id;
        this.bills = bills;
        this.products = products;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
