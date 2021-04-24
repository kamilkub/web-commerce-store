package org.store.ecommercestore.repository;


import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.CartEntity;

import java.util.Optional;

@Repository
public interface CartEntityRepository extends AbstractRepository<CartEntity, Long> {
    Optional<CartEntity> findByCartHolderEmail(String email);
}
