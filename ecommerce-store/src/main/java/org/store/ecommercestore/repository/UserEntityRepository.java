package org.store.ecommercestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends AbstractRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByToken(String token);
    List<UserEntity> findAll();



    //
    // #Pagination methods
    Page<UserEntity> findAll(Pageable pageable);
    Page<UserEntity> findByEmailContaining(String email, Pageable pageable);
}
