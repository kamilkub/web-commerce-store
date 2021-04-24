package org.store.ecommercestore.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.store.ecommercestore.mapper.mapperhelper.ProductImage;
import org.store.ecommercestore.model.ProductEntity;


@Setter
@Getter
@NoArgsConstructor
public class ProductRequest {

        private ProductImage productImage;
        private ProductEntity productInfo;
}
