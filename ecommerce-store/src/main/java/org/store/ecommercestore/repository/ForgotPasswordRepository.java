package org.store.ecommercestore.repository;

import org.springframework.stereotype.Repository;
import org.store.ecommercestore.model.ForgotPasswordToken;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends AbstractRepository<ForgotPasswordToken, Long>{

    Optional<ForgotPasswordToken> findByForgotPasswordToken(String token);
}
