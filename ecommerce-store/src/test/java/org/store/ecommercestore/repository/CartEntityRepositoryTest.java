package org.store.ecommercestore.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.CartEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartEntityRepositoryTest {


    @Autowired
    private CartEntityRepository cartEntityRepository;

    private static CartEntity testCart = new CartEntity();

    static {
        testCart.setCartHolderEmail("test_mail@test.com");
    }

    @BeforeAll
    void init(){
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartHolderEmail("sample@test.com");
        cartEntityRepository.save(cartEntity);
    }

    @Test
    @DisplayName("TEST :: adding cart")
    void findByCartHolderEmail() {
        cartEntityRepository.save(testCart);
        assertThat(cartEntityRepository.findAll().size(), equalTo(3));
    }

    @Test
    @DisplayName("TEST :: deleting cart")
    void passIfCartIsDeleted(){
        cartEntityRepository.delete(testCart);
        assertThat(cartEntityRepository.findAll().size(), equalTo(2));
    }

    @Test
    @DisplayName("TEST :: updating cart")
    void passIfCartIsUpdated(){
        CartEntity cartEntity = cartEntityRepository.findById(1L).get();
        cartEntity.setCartHolderEmail("update_email@test.com");
        cartEntityRepository.save(cartEntity);
        assertThat(cartEntityRepository.findById(1L).get().getCartHolderEmail(), equalTo("update_email@test.com"));
    }



}