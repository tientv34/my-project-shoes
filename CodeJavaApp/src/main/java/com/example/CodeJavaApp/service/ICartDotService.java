package com.example.CodeJavaApp.service;

import com.example.CodeJavaApp.entity.CartDot;


import java.util.ArrayList;
import java.util.Collection;


public interface ICartDotService {
    void add(CartDot item);

    void deleteCart(Long id);

    CartDot EditCart(Long id, int quantity);

    void clearCart();

    Collection<CartDot> getAllItems();

    int getCount();

    double getAmount();

}
