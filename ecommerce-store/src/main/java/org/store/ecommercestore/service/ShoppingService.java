package org.store.ecommercestore.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.model.*;
import org.store.ecommercestore.repository.CartEntityRepository;
import org.store.ecommercestore.repository.OrderRepository;
import org.store.ecommercestore.repository.ProductEntityRepository;
import org.store.ecommercestore.repository.UserEntityRepository;
import org.store.ecommercestore.security.HelperSecurityHolder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Instance of this class takes care of users products, as well as allows to get all of the products of shop
 */

@Service
public class ShoppingService {

    @Autowired
    private CartEntityRepository cartEntityRepository;

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingService.class);


    /**
     *
     * @return all products that exist in database should be returned
     */
    public Page<ProductEntity> getAllProducts(int page, int size){
        return productEntityRepository.findAll(PageRequest.of(page, size));
    }


    /**
     *
     * @param categoryName categoryEntity name -
     * @return returns all found products in database by specified category
     */

    public Page<ProductEntity> getAllProductsByCategory(int page, int size, String categoryName){
        return productEntityRepository.findByCategoryCategoryName(categoryName, PageRequest.of(page, size));
    }

    /**
     * This method adds product to users cart - everything is done through productId
     * @param id productEntity ID
     */

    public void addProductToCart(Long id){
        Optional<ProductEntity> product = productEntityRepository.findById(id);
        String username = HelperSecurityHolder.getAuthenticatedUsername();

        userEntityRepository.findByUsername(username).ifPresent(userEntity -> {
            Set<ProductEntity> userProducts = userEntity.getCartEntity().getProducts();
            product.ifPresent(userProducts::add);
            userEntity.getCartEntity().setProducts(userProducts);

//            cartEntityRepository.save(userEntity.getCartEntity());
            userEntityRepository.save(userEntity);
        });
    }


    public List<OrderEntity> getUserOrders(){
        String username = HelperSecurityHolder.getAuthenticatedUsername();

        Optional<UserEntity> userObj = userEntityRepository.findByUsername(username);

        return userObj.isPresent() ? orderRepository.findByOrderHolder(userObj.get()) : Collections.emptyList();
    }

    public void createOrder(OrderEntity orderEntity){
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setOrderProducts(new ArrayList<>(showCurrentProductsInCart()));

        Optional<UserEntity> byUsername = userEntityRepository.findByUsername(HelperSecurityHolder.getAuthenticatedUsername());

        byUsername.ifPresent(orderEntity::setOrderHolder);

        orderRepository.save(orderEntity);
    }

    /**
     * Method responsible for deleting product from users' cart
     *
     * @param id product id - must be id of any product in cart user
     */

    public void deleteProductFromCart(Long id){
        String username = HelperSecurityHolder.getAuthenticatedUsername();

        userEntityRepository.findByUsername(username).ifPresent(userEntity -> {
            Set<ProductEntity> collect = userEntity.getCartEntity().getProducts();
            Optional<ProductEntity> product = productEntityRepository.findById(id);
            product.ifPresentOrElse(productFound -> {
                collect.remove(productFound);
                CartEntity cartEntity = userEntity.getCartEntity();
                cartEntity.setProducts(collect);
                cartEntityRepository.save(cartEntity);
            }, () -> logger.error("org.store.ecommercestore.service.ShoppingService: product doesn't exist"));

        });
    }

    /**
     *
     * @return returns users' current total price of products in cart
     */

    public BigDecimal getCurrentCartPrice(){
        String username = HelperSecurityHolder.getAuthenticatedUsername();
        BigDecimal price = new BigDecimal(0L);
        List<BigDecimal> prices =  userEntityRepository.findByUsername(username)
                .get()
                .getCartEntity()
                .getProducts().stream().map(ProductEntity::getPrice).collect(Collectors.toList());
        
        for(BigDecimal a : prices){
            price = price.add(a);
        }

        return price;
    }

    /**
     *
     * Removes all products from cart of currently logged in user
     *
     */

    public void removeAllProductsFromCart(){
        String username = HelperSecurityHolder.getAuthenticatedUsername();
        UserEntity userEntity = userEntityRepository.findByUsername(username).get();
        CartEntity byCartHolderEmail = cartEntityRepository.findByCartHolderEmail(userEntity.getEmail()).get();
        byCartHolderEmail.setProducts(new HashSet<>());
        cartEntityRepository.save(byCartHolderEmail);
    }

    /**
     *
     * @return method responsible of returning currently logged in user products in cart
     */

    public Set<ProductEntity> showCurrentProductsInCart(){
        String username = HelperSecurityHolder.getAuthenticatedUsername();
        return userEntityRepository.findByUsername(username).map(userEntity -> userEntity.getCartEntity().getProducts()).get();
    }




}
