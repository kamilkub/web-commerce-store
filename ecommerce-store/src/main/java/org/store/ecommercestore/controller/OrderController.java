package org.store.ecommercestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.store.ecommercestore.model.OrderEntity;
import org.store.ecommercestore.repository.OrderRepository;
import org.store.ecommercestore.repository.UserEntityRepository;
import org.store.ecommercestore.service.EmailService;
import org.store.ecommercestore.service.ShopManagementService;
import org.store.ecommercestore.service.ShoppingService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ShopManagementService shopManagementService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private OrderRepository orderEntityRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create-order")
    public ResponseEntity<Object> createOrder(@RequestBody @Valid OrderEntity orderEntity){
        shoppingService.createOrder(orderEntity);
        emailService.sendOrderInfo(orderEntity.getBuyerEmail(), orderEntity.getOrderProducts(), shoppingService.getCurrentCartPrice().toString());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-orders")
    public Object getUserOrders(){
        return new ResponseEntity<>(shoppingService.getUserOrders(), HttpStatus.OK);
    }

    @GetMapping("/orders/byId")
    public Object getOrderById(@RequestParam("orderId") Long id){
        Optional<OrderEntity> byId = orderEntityRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        return byId.get();
    }

    @PutMapping("/update-order")
    public OrderEntity updateOrder(@RequestBody OrderEntity orderEntity){
        return orderEntityRepository.save(orderEntity);
    }

    @DeleteMapping("/delete-order")
    public void deleteOrder(@RequestParam("orderId") Long id){
        orderEntityRepository.deleteById(id);
    }


    /*
        Pagination mappings
     */

    @GetMapping("orders-by-username")
    public Page<OrderEntity> getOrdersByUsername(@RequestParam("username") String username,
                                                @RequestParam("page") int page,
                                                 @RequestParam("size") int size){
        return shopManagementService.getOrdersByUsername(username, page, size);
    }

    @GetMapping("/orders")
    public Page<OrderEntity> getAllOrders(@RequestParam("page") int page, @RequestParam("size") int size){
        return orderEntityRepository.findAll(PageRequest.of(page, size));
    }


}
