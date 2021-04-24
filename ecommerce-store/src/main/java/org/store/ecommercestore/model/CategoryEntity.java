package org.store.ecommercestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class CategoryEntity extends AbstractEntity<Long> {


    @NotBlank(message = "Category name cannot be empty")
    @Column(unique = true)
    private String categoryName;

}
