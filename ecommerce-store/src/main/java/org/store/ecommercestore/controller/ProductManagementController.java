package org.store.ecommercestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.store.ecommercestore.model.CategoryEntity;
import org.store.ecommercestore.model.ProductEntity;
import org.store.ecommercestore.service.ShopManagementService;
import org.store.ecommercestore.service.ShoppingService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductManagementController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ShopManagementService shopManagementService;

    @GetMapping("/all-products")
    public Page<ProductEntity> getAllProducts(@RequestParam("page") int page, @RequestParam("size") int size){
        return shoppingService.getAllProducts(page, size);
    }

    @GetMapping("/products-by-category")
    public Page<ProductEntity> getProductsByCategory(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("categoryName") String categoryName){
        return shoppingService.getAllProductsByCategory(page, size, categoryName);
    }

    @GetMapping("/all-categories")
    public List<CategoryEntity> getAllCategories() {
        return shopManagementService.getCategories();
    }

    @GetMapping("/product-by-id")
    public ProductEntity getProductById(@RequestParam("productId") Long id){
        return shopManagementService.getProductById(id);
    }


    /*
        Pagination mappings
     */

    @GetMapping("/all-categories-pag")
    public Page<CategoryEntity> getAllCategoriesPagination(@RequestParam("page") int page,
                                                           @RequestParam("size") int size) {
        return shopManagementService.getPaginationCategories(page, size);
    }

    @GetMapping("/categories-by-name")
    public Page<CategoryEntity> getCategoriesByName(@RequestParam("categoryName") String name,@RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        return shopManagementService.getCategoriesByName(name, page, size);
    }


    @GetMapping("/products-by-name")
    public Page<ProductEntity> getProductsByName(@RequestParam("productName") String productName,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size){
        return shopManagementService.findProductsByName(productName, page, size);
    }


}
