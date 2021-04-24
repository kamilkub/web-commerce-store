package org.store.ecommercestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderEntity extends AbstractEntity<Long> {


    @NotBlank
    private String buyerFirstName;
    @NotBlank
    private String buyerSecondName;
    @NotBlank
    private String buyerEmail;
    @NotBlank
    private String shippingCountry;
    @NotBlank
    private String shippingStreet;
    @NotBlank
    private String shippingCity;
    @NotBlank
    private String shippingRegion;
    @NotBlank
    private String shippingPostalCode;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProductEntity> orderProducts;

    @ManyToOne
    private UserEntity orderHolder;

}
