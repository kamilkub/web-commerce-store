package org.store.ecommercestore.repository;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.ForgotPasswordToken;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ForgotPwdRepositoryTest {


    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;


    private static ForgotPasswordToken sampleToken = new ForgotPasswordToken();

    private static final String token = UUID.randomUUID().toString();

    @BeforeAll
    void init() {
        sampleToken.setForgotPasswordEmail("test@domain.com");
        sampleToken.setTokenExpiry(LocalDateTime.now().plusDays(1));
        sampleToken.setForgotPasswordToken(token);

        forgotPasswordRepository.save(sampleToken);
    }

    @Order(1)
    @Test
    void passIfTokenAdded() {
        forgotPasswordRepository.save(sampleToken);
        assertThat(forgotPasswordRepository.findAll().size(), equalTo(1));
    }

    @Order(2)
    @Test
    void passIfTokenUpdated() {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findAll().get(0);
        forgotPasswordToken.setForgotPasswordEmail("updated@domain.com");
        forgotPasswordRepository.save(forgotPasswordToken);

        assertThat(forgotPasswordRepository.findAll().get(0).getForgotPasswordEmail(), equalTo("updated@domain.com"));
    }


    @Order(4)
    @Test
    void passIfTokenDeleted() {
        forgotPasswordRepository.delete(sampleToken);
        assertTrue(forgotPasswordRepository.findAll().isEmpty());
    }

    @Order(3)
    @Test
    void passIfTokenFoundByTokenValue(){
        assertFalse(forgotPasswordRepository.findByForgotPasswordToken(token).isEmpty());
    }
}
