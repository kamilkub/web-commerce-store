package org.store.ecommercestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.CategoryEntity;

import java.util.List;

@Repository
public interface CategoryEntityRepository extends AbstractRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAll();

    //
    // #Pagination methods
    Page<CategoryEntity> findAll(Pageable pageable);
    Page<CategoryEntity> findByCategoryNameContaining(String categoryName, Pageable pageable);

}
