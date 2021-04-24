package org.store.ecommercestore.mapper;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUser {
    private String email;
    private String username;
    private String role;
    private boolean isAuth;

    public AuthenticatedUser(String email,String username, String role, boolean isAuthenticated) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.isAuth = isAuthenticated;
    }
}
