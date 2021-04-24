package org.store.ecommercestore.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.store.ecommercestore.model.AbstractEntity;

import java.util.List;


public interface AbstractRepository<T extends AbstractEntity, ID> extends PagingAndSortingRepository<T, ID> {
        List<T> findAll();
}
