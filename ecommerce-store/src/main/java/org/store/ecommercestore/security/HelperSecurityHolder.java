package org.store.ecommercestore.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class HelperSecurityHolder {

    public static String getAuthenticatedUsername(){
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return  userDetails.getUsername();
    }

    public static boolean isAuthenticated(){
        return  SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
