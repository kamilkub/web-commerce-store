package org.store.ecommercestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.OrderEntity;
import org.store.ecommercestore.model.UserEntity;

import java.util.List;

@Repository
public interface OrderRepository extends AbstractRepository<OrderEntity, Long> {

    List<OrderEntity> findByOrderHolder(UserEntity userEntity);
    List<OrderEntity> findAll();

    //
    // #Pagination methods
    Page<OrderEntity> findByOrderHolderUsername(String username, Pageable pageable);
}
