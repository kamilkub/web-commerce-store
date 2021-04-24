package org.store.ecommercestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartEntity extends AbstractEntity<Long> {


    private String cartHolderEmail;

    @ManyToMany
    private Set<ProductEntity> products;

}
