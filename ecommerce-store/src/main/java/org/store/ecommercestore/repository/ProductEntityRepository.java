package org.store.ecommercestore.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.ProductEntity;

import java.util.List;


@Repository
public interface ProductEntityRepository extends AbstractRepository<ProductEntity, Long> {

    List<ProductEntity> findAll();
    List<ProductEntity> findByNameContaining(String name);

    //
    // #Pagination methods
    Page<ProductEntity> findByNameContaining(String name, Pageable pageable);
    Page<ProductEntity> findByCategoryCategoryName(String categoryName, Pageable pageable);
    Page<ProductEntity> findAll(Pageable pageable);

}
