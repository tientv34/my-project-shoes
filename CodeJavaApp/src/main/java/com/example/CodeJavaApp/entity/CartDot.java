package com.example.CodeJavaApp.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;


public class CartDot {
    private int quantity;
    private double totalPrice;
    private Products product;

    public CartDot() {
    }

    public CartDot( int quantity, double totalPrice, Products product) {

        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
