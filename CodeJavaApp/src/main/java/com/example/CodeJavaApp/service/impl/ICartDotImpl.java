package com.example.CodeJavaApp.service.impl;

import com.example.CodeJavaApp.entity.CartDot;
import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.repository.ProductRepository;
import com.example.CodeJavaApp.service.ICartDotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Service
@SessionScope
public class ICartDotImpl implements ICartDotService {
    @Autowired
    ProductServiceImpl productService = new ProductServiceImpl();
    @Autowired
    ProductRepository repo;
    Map<Long,CartDot> cartDotMap = new HashMap<>();
    @Override
    public void add(CartDot item){
        CartDot cartDot = cartDotMap.get(item.getProduct().getId_prd());
        if (cartDot == null){
            cartDotMap.put(item.getProduct().getId_prd(),item);
        }else {
            System.out.println("cartDot:" +cartDot);
            cartDot.setQuantity(cartDot.getQuantity() + 1);
        }
    }

    @Override
    public void deleteCart(Long id){
        cartDotMap.remove(id);
    }

    @Override
    public CartDot EditCart(Long id, int quantity){
        CartDot cartDot = cartDotMap.get(id);
        cartDot.setQuantity(quantity);
        return cartDot;
    }

    @Override
    public void clearCart(){
        cartDotMap.clear();
    }

    @Override
    public Collection<CartDot> getAllItems(){
        return cartDotMap.values();
    }

    @Override
    public int getCount(){
        return cartDotMap.values().size();
    }

    @Override
    public double getAmount(){
        double totalPricce = cartDotMap.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        return totalPricce;
    }




//    @Override
//    public HashMap<Long, CartDot> AddCart(Long id, HashMap<Long, CartDot> cart) {
//        CartDot itemCart = new CartDot();
//        Optional<Products> prd = productService.findPrdById(id);
//        System.out.println("prd: "+prd);
//        if (prd != null && cart.containsKey(id)) {
//            itemCart= cart.get(id);
//            System.out.println("itemcart: "+ itemCart);
//            itemCart.setQuantity(itemCart.getQuantity() +1);
//
//            itemCart.setTotalPrice(itemCart.getQuantity() * itemCart.getProduct().getPrice());
//        }else {
//            System.out.println("itemCart: "+ cart.get(id));
//           Products product = prd.get();
//            System.out.println("product: "+product);
//            itemCart.setProduct(product);
//            itemCart.setQuantity(1);
//            itemCart.setTotalPrice(prd.get().getPrice());
//            System.out.println("itemCart: " +itemCart);
//        }
//        cart.put(id, itemCart);
//        System.out.println("itemCart: "+ cart);
//        return cart;
//    }
//
//    @Override
//    public HashMap<Long, CartDot> EditCart(Long id, Integer quantity, HashMap<Long, CartDot> cart) {
//        if (cart == null) {
//            return cart;
//        }
//        CartDot itemCart = new CartDot();
//        if (cart.containsKey(id)) {
//            itemCart = cart.get(id); //trả lại 1 đối tượng là CartDot.
//            itemCart.setQuantity(quantity);
//            double totalPrice = quantity * itemCart.getProduct().getPrice();
//            itemCart.setTotalPrice(totalPrice);
//        }
//        cart.put(id, itemCart);
//        return cart;
//    }
//
//    @Override
//    public HashMap<Long, CartDot> DeleteCart(String id, HashMap<Long, CartDot> cart) {
//        if (cart == null) {
//            return cart;
//        }
//        if (cart.containsKey(id)) {
//            cart.remove(id);
//        }
//        return cart;
//    }
//
//    @Override
//    public Integer totalQuantity(HashMap<Long, CartDot> cartDotHashMap) {
//        int totalQuantity = 0;
//        for (Map.Entry<Long,CartDot> itemCart : cartDotHashMap.entrySet()) {
//            totalQuantity += itemCart.getValue().getQuantity();
//        }
//        return totalQuantity;
//    }
//
//    @Override
//    public Double totalPrice(HashMap<Long, CartDot> cartDotHashMap) {
//        double totalPrice = 0;
//        for (Map.Entry<Long,CartDot> itemCart : cartDotHashMap.entrySet()) {
//            totalPrice += itemCart.getValue().getProduct().getPrice();
//        }
//        return totalPrice;
//    }


}

