package org.store.ecommercestore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordToken extends AbstractEntity<Long>{


    private String forgotPasswordEmail;
    private String forgotPasswordToken;

    private LocalDateTime tokenExpiry = LocalDateTime.now().plusHours(24);

    public ForgotPasswordToken(String forgotPasswordEmail, String forgotPasswordToken) {
        this.forgotPasswordEmail = forgotPasswordEmail;
        this.forgotPasswordToken = forgotPasswordToken;
    }
}
