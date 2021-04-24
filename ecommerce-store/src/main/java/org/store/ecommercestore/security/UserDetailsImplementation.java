package org.store.ecommercestore.security;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.model.UserEntity;
import org.store.ecommercestore.repository.UserEntityRepository;

import java.util.Optional;

@Service
public class UserDetailsImplementation implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsImplementation.class);

    public UserDetailsImplementation(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);

        if(userEntity.isPresent())
            return userEntity.get();
        else
            try {
                throw new NotFoundException("User with this username is not present");
            } catch (NotFoundException e) {
                logger.error(e.getMessage(), e.getCause());
                return null;
            }

    }
}
