package org.store.ecommercestore.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private static final String userToken = UUID.randomUUID().toString();

    @BeforeAll
    @DisplayName(value = "Sample data initialization ():")
    void addSampleUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("testEmail@domain.com");
        userEntity.setUsername("testUsername");
        userEntity.setPassword("test_password");
        userEntity.setRole("ROLE_USER");
        userEntity.setToken(userToken);
        userEntityRepository.save(userEntity);
    }

    @AfterEach
    void cleanUp(){
        userEntityRepository.deleteAll();
    }


    @Test
    @DisplayName("findByEmail(): method testing")
    void shouldReturnUserByEmailIfExisting() {
        assertTrue(userEntityRepository.findByEmail("testEmail@domain.com").isPresent());
    }

    @Test
    @DisplayName("findByToken(): method testing")
    void shouldReturnUserByTokenIfExisting(){
        assertTrue(userEntityRepository.findByToken(userToken).isPresent());
    }

    @Test
    @DisplayName("findByUsername(): method testing")
    void shouldReturnUserByUsernameIfExisting(){
        assertTrue(userEntityRepository.findByUsername("testUsername").isPresent());
    }

    @Test
    @DisplayName("delete(): method testing")
    void shouldDeleteSelectedUser () {
        Optional<UserEntity> deleteUserObject = userEntityRepository.findByUsername("testUsername");
        assertTrue(deleteUserObject.isPresent());
        System.out.println(userEntityRepository.findAll().size());
        userEntityRepository.delete(deleteUserObject.get());
        assertEquals(2, userEntityRepository.findAll().size());
    }
}