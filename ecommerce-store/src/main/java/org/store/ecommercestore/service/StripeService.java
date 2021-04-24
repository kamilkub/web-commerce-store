package org.store.ecommercestore.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.model.ProductEntity;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class responsible for Stripe Payment integration
 *
 */

@Service
public class StripeService {


    @Value("${stripe.secret.key}")
    private String STRIPE_API_KEY;

    @Autowired
    private ShoppingService shoppingService;

    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);


    /**
     *  Creates stripe payment with user cart products
     *
     * @param userCart Currently logged in user cart products
     * @return returns checkout session id which can be used to redirect to Stripe Check-out page
     */
    public Map<String, String> createCheckoutSession(Set<ProductEntity> userCart){

        Stripe.apiKey = STRIPE_API_KEY;

        List<SessionCreateParams.LineItem> orderItems = new ArrayList<>();

        userCart.forEach(cartProduct -> {
            SessionCreateParams.LineItem orderItem = SessionCreateParams.LineItem.builder()
                    .setDescription(cartProduct.getDescription())
                    .setQuantity(1L)
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmountDecimal(BigDecimal.valueOf(cartProduct.getPrice().doubleValue() * 100))
                            .setProductData(SessionCreateParams.LineItem
                                                        .PriceData.ProductData.builder()
                            .setName(cartProduct.getName()).build()).build()
                    ).build();

            orderItems.add(orderItem);
        });

        SessionCreateParams checkOutSession =
                SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/order-tracking/success")
                .setCancelUrl("http://localhost:4200/payment-failure")
                .addAllLineItem(orderItems).build();

        try {
            Session session = Session.create(checkOutSession);
            HashMap<String, String> response = new HashMap<>();
            response.put("sessionID", session.getId());
            shoppingService.removeAllProductsFromCart();
            return response;
        } catch (StripeException e) {
            logger.error(e.getMessage(), e.getStripeError());
            return null;
        }


    }





}
