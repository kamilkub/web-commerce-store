package org.store.ecommercestore.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.OrderEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {


        @Autowired
        private OrderRepository orderRepository;

        private static OrderEntity sampleOrder = new OrderEntity();

        @BeforeEach
        void init(){
            sampleOrder.setShippingStreet("test");
            sampleOrder.setShippingRegion("test");
            sampleOrder.setShippingCountry("test");
            sampleOrder.setBuyerFirstName("test");
            sampleOrder.setShippingCity("test");
            sampleOrder.setBuyerSecondName("test");
            sampleOrder.setShippingPostalCode("test");
            sampleOrder.setBuyerEmail("sample@test.com");
            orderRepository.save(sampleOrder);
        }

        @Test
        @DisplayName("TEST :: adding order")
        void passIfOrderIsAdded(){
        orderRepository.save(sampleOrder);
        assertThat(orderRepository.findAll().size(), equalTo(2));
    }

    @Test
    @DisplayName("TEST :: deleting order")
    void passIfOrderIsDeleted(){
        orderRepository.delete(sampleOrder);
        assertThat(orderRepository.findAll().size(), equalTo(1));
    }

    @Test
    @DisplayName("TEST :: updating order")
    void passIfOrderIsUpdated(){
        OrderEntity orderEntity = orderRepository.findAll().get(0);
        orderEntity.setBuyerEmail("update@test.com");
        assertThat(orderRepository.findAll().get(0).getBuyerEmail(), equalTo("update@test.com"));
    }

}
