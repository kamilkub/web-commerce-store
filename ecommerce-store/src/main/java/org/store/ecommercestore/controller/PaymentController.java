package org.store.ecommercestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.ecommercestore.service.ShoppingService;
import org.store.ecommercestore.service.StripeService;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private ShoppingService shoppingService;


    @GetMapping("/stripe/session-id")
    public Map<String, String> createStripPayment(){
        return stripeService.createCheckoutSession(shoppingService.showCurrentProductsInCart());
    }

}
