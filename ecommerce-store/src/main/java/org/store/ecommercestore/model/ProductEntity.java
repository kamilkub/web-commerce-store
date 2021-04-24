package org.store.ecommercestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductEntity extends AbstractEntity<Long> {

    private Long unitsInStock;

    @NotBlank
    private String name;

    private BigDecimal price;

    @NotBlank
    @Column(length = 10000)
    private String description;


    private String imageUrl;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;



}
