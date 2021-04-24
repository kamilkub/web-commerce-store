package org.store.ecommercestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.store.ecommercestore.model.ProductEntity;
import org.store.ecommercestore.service.ShoppingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private ShoppingService shoppingService;

    @GetMapping("/add-product")
    public void addProductToCart(@RequestParam("productId") Long id){
        shoppingService.addProductToCart(id);
    }

    @GetMapping("/all-products")
    public Set<ProductEntity> getAllCurrentCartProducts(){
        return shoppingService.showCurrentProductsInCart();
    }

    @DeleteMapping("/delete-product")
    public void deleteProductFromCart(@RequestParam("productId") Long id){
        shoppingService.deleteProductFromCart(id);
    }


    @DeleteMapping("/deleteCart-products")
    public void deleteAllCartProducts(){
        shoppingService.removeAllProductsFromCart();
    }

    @GetMapping("/current-price")
    public Map<String, Double> getCurrentPrice(){
        HashMap<String, Double> data = new HashMap<>();
        data.put("cartPrice", shoppingService.getCurrentCartPrice().doubleValue());
        return data;
    }

    @GetMapping("/product-count")
    public Map<String, Integer> getProductCount(){
        HashMap<String, Integer> data = new HashMap<>();
        data.put("cartCount", shoppingService.showCurrentProductsInCart().size());
       return data;
    }

}
