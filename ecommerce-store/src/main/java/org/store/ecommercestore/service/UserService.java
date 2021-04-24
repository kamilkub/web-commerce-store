package org.store.ecommercestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.model.UserEntity;
import org.store.ecommercestore.repository.CartEntityRepository;
import org.store.ecommercestore.repository.UserEntityRepository;

import java.util.Optional;

/**
 * UserService.class - contains CRUD-operations on Users(UserEntity.class) which use UserEntityRepository.class
 */
@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private CartEntityRepository cartEntityRepository;

    public void deleteUserById(Long id){
        Optional<UserEntity> byId = userEntityRepository.findById(id);
        byId.ifPresent(userObject -> {
            userEntityRepository.deleteById(id);
            cartEntityRepository.delete(userObject.getCartEntity());
        });


    }

    public Page<UserEntity> getUsersByEmail(String email, int page, int size){
        return userEntityRepository.findByEmailContaining(email, PageRequest.of(page, size));
    }

    public void updateUser(UserEntity userEntity){
        userEntityRepository.findById(userEntity.getId()).ifPresent(userObject -> userEntityRepository.save(userEntity));
    }

    public Optional<UserEntity> getUserById(Long id){
        return userEntityRepository.findById(id);
    }

    public Page<UserEntity> getAllUsers(int page, int size){
        return userEntityRepository.findAll(PageRequest.of(page, size));
    }


}
