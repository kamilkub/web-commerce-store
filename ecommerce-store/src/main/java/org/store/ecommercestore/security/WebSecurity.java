package org.store.ecommercestore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.store.ecommercestore.exceptions.SimpleExceptionHandler;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsImplementation userDetailsImplementation;

    @Autowired
    private SimpleExceptionHandler simpleExceptionHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsImplementation)
                .passwordEncoder(bCrypt());
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console")
            .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.exceptionHandling().accessDeniedHandler(simpleExceptionHandler);
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/admin-panel/*").hasRole("ADMIN")
                .antMatchers("/products/*").permitAll()
                .antMatchers("/payment/stripe/session-id").permitAll()
                .antMatchers("/user/sign-up").permitAll()
                .antMatchers("/user/facebook-user").permitAll()
                .antMatchers("/user/forgot-password").permitAll()
                .antMatchers("/user/change-password").permitAll()
                .antMatchers("/user/token-activation").permitAll()
                .antMatchers("/payment/stripe/make-payment").permitAll()
                .antMatchers("/cart/*").hasRole("USER")
                .antMatchers("/order/create-order").hasRole("USER")
                .antMatchers("/order/user-orders").hasRole("USER")
                .antMatchers("/order/orders").hasRole("ADMIN")
                .antMatchers("/order/delete-order").hasRole("ADMIN")
                .antMatchers("/order/update-order").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic();

        http.cors().configurationSource(corsConfigurationSource());


    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        return httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("http://localhost:4200");
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            return corsConfiguration;
        };
    }


    @Bean
    public PasswordEncoder bCrypt(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() throws Exception {
        return super.authenticationManager();
    }
}
