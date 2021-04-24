package org.store.ecommercestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.store.ecommercestore.mapper.ProductRequest;
import org.store.ecommercestore.mapper.Response;
import org.store.ecommercestore.model.CategoryEntity;
import org.store.ecommercestore.model.UserEntity;
import org.store.ecommercestore.service.ShopManagementService;
import org.store.ecommercestore.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Rest-Controller, contains ADMIN authenticated endpoints
 */

@RestController
@RequestMapping("/admin-panel")
public class AdminController {


    @Autowired
    private ShopManagementService shopManagementService;

    @Autowired
    private UserService userService;



    @PostMapping(value = "/add-product")
    public ResponseEntity<Object> addProductEndpoint(@RequestBody ProductRequest addProductRequest, BindingResult bindingResult){

        if(addProductRequest.getProductImage().getImageBase64().isEmpty() || !addProductRequest.getProductImage().getImageBase64().contains("image")){
            return new ResponseEntity<>(new Response("Product image is not present"
                    , HttpStatus.BAD_REQUEST, false)
                    , HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST, false, errors), HttpStatus.BAD_REQUEST);
        }

        if(shopManagementService.addProduct(addProductRequest.getProductInfo(), addProductRequest.getProductImage())){
            return new ResponseEntity<>(HttpStatus.OK);
        }


        return new ResponseEntity<>("Could not add the product, something went wrong", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/add-category")
    public ResponseEntity<Object> addCategoryEndpoint(@RequestBody @Valid CategoryEntity categoryEntity, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        shopManagementService.addCategory(categoryEntity);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/byId")
    public Object getUserById(@RequestParam("userId") Long id){
        Optional<UserEntity> userById = userService.getUserById(id);
        if(userById.isEmpty()){
            return ResponseEntity.badRequest();
        }
        return userById.get();
    }

    @DeleteMapping("/users")
    public void deleteUserByIdEndpoint(@RequestParam("userId") Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUserEndpoint(@RequestBody @Valid UserEntity userEntity, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return ResponseEntity.badRequest().build();
        userService.updateUser(userEntity);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-category")
    public void deleteCategoryEndpoint(@RequestParam("categoryId") Long id){
        shopManagementService.deleteCategory(id);
    }

    @DeleteMapping("/delete-product")
    public void deleteProductEndpoint(@RequestParam("productId") Long id){
        shopManagementService.deleteProductById(id);
    }

    @PutMapping("/edit-product")
    public ResponseEntity<Object> editProductEndpoint(@RequestBody ProductRequest editProductRequest){
        if(shopManagementService.editProduct(editProductRequest))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("Something went wrong while updating the product", HttpStatus.BAD_REQUEST);
    }

     /*
       Pagination mappings
    */


    @GetMapping("/users")
    public Page<UserEntity> getAllUsersEndpoint(@RequestParam("page") int page, @RequestParam("size") int size){
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/users-by-email")
    public Page<UserEntity> getUsersByEmail(@RequestParam("email") String email,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size){
        return userService.getUsersByEmail(email, page, size);
    }



}
