package org.store.ecommercestore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserEntity extends AbstractEntity<Long> implements UserDetails {


    @NotBlank(message = "Email can't be empty")
    @Email(message = "This email address is not valid")
    private String email;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Username can't be empty")
    @Size(min = 6, message = "Password is to short, consider longer password. At least 6 characters")
    private String password;


    private String role;

    private String token;

    private String platform = "THIS";

    @OneToOne(cascade = CascadeType.REMOVE)
    private CartEntity cartEntity;


    private boolean isAccountEnabled = false;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }


    public void setAccountEnabled(boolean accountEnabled) {
        this.isAccountEnabled = accountEnabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }
}
