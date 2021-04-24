package org.store.ecommercestore.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.CategoryEntity;
import org.store.ecommercestore.model.ProductEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductEntityRepositoryTest {


    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    private static ProductEntity sampleProduct = new ProductEntity();


    @BeforeAll
    void init(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("test");
        categoryEntityRepository.save(categoryEntity);
        sampleProduct.setName("test product");
        sampleProduct.setDescription("test descp");
        sampleProduct.setCategory(categoryEntity);
        ProductEntity saved = productEntityRepository.save(sampleProduct);

    }

    @Test
    @Order(1)
    void passIfProductIsAdded(){
        productEntityRepository.save(sampleProduct);
        assertThat(productEntityRepository.findAll().size(), equalTo(17));
    }

    @Test
    @Order(2)
    void passIfProductIsUpdated(){
        ProductEntity productEntity = productEntityRepository.findById(1L).get();
        productEntity.setDescription("update desc");
        productEntityRepository.save(productEntity);
        assertThat(productEntityRepository.findById(1L).get().getDescription(), equalTo("update desc"));
    }

    @Test
    @Order(4)
    void passIfProductIsDeleted(){
        productEntityRepository.delete(sampleProduct);
        assertThat(productEntityRepository.findAll().size(), equalTo(16));
    }

    @Test
    @Order(3)
    void passIfProductIsFoundByNameContaining(){
        assertFalse(productEntityRepository.findByNameContaining("test").isEmpty());
    }


}
