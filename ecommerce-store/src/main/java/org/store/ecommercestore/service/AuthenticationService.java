package org.store.ecommercestore.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.mapper.AuthenticatedUser;
import org.store.ecommercestore.mapper.FacebookUserData;
import org.store.ecommercestore.mapper.ForgotPasswordMapper;
import org.store.ecommercestore.model.CartEntity;
import org.store.ecommercestore.model.ForgotPasswordToken;
import org.store.ecommercestore.model.UserEntity;
import org.store.ecommercestore.repository.CartEntityRepository;
import org.store.ecommercestore.repository.ForgotPasswordRepository;
import org.store.ecommercestore.repository.UserEntityRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class mainly represents methods for user sign-up, as well as logging in and other
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartEntityRepository cartEntityRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    /**
     *
     * @param userEntity user object, which will be saved in database for later authentication
     * @return returns true if everything is alright, else false
     */
    public boolean signUp(UserEntity userEntity){
        if(!userExists(userEntity)){
            encodePassword(userEntity);
            setUserRole(userEntity);
            createCartForUser(userEntity);
            saveUser(userEntity);
            return true;
        }
        return false;
    }

    public ResponseEntity facebookRegister(FacebookUserData facebookUserData){
        if(facebookUserData == null) return ResponseEntity.badRequest().build();
        Optional<UserEntity> userOptional = Optional.ofNullable(getUserByEmail(facebookUserData.getEmail()));



        if(userOptional.isEmpty()){
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(facebookUserData.getEmail());
            userEntity.setUsername(facebookUserData.getName().substring(0,5) + generateRandom());
            userEntity.setAccountEnabled(true);
            userEntity.setPlatform("FACEBOOK");
            userEntity.setPassword(encodePassword(facebookUserData.getId()));
            setUserRole(userEntity);
            createCartForUser(userEntity);
            saveUser(userEntity);

            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.setEmail(userEntity.getEmail());
            authenticatedUser.setAuth(userEntity.isEnabled());
            authenticatedUser.setUsername(userEntity.getUsername());
            authenticatedUser.setRole(userEntity.getAuthorities().toString());
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } else {

            if(userOptional.get().getPlatform().equals("FACEBOOK")) {
                AuthenticatedUser authenticatedUser = new AuthenticatedUser();
                authenticatedUser.setAuth(userOptional.get().isEnabled());
                authenticatedUser.setEmail(userOptional.get().getEmail());
                authenticatedUser.setUsername(userOptional.get().getUsername());
                authenticatedUser.setRole(userOptional.get().getAuthorities().toString());
                return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
            } else {

                return new ResponseEntity<>("You already have an account", HttpStatus.CONFLICT);
            }

        }


    }

    private String generateRandom(){
        return UUID.randomUUID().toString().substring(0,5);
    }


    /**
     *
     * @return returns object of authenticated user which can be used for later authentication
     */
    public AuthenticatedUser signIn(){

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserEntity userEntity = ((UserEntity) auth.getPrincipal());

            return new AuthenticatedUser(userEntity.getEmail(),userEntity.getUsername(),
                    userEntity.getAuthorities().toString(), true);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage(), e.fillInStackTrace());
            return null;
        }
    }

    /**
     *
     * @param token variable send with email verification, allows to activate an account
     * @return if successful returns true, else false
     */

    public boolean activateUserAccount(String token){
        Optional<UserEntity> byToken = userEntityRepository.findByToken(token);
        byToken.ifPresent(this::setAccountEnabled);
        return true;
    }

    /**
     * Responsible for checking if user who requested for changing password is existing, if yes, creating forgotPassword token,then sending it on email
     * @param forgotPasswordMapper request for forgotPassword
     * @return if successful returns true, else false
     */

    public boolean changePasswordLink(ForgotPasswordMapper forgotPasswordMapper){
        AtomicBoolean success = new AtomicBoolean(false);
        String userEmail = forgotPasswordMapper.getEmail();
        Optional<UserEntity> userEntity = userEntityRepository.findByEmail(userEmail);
        String forgotPwdToken = UUID.randomUUID().toString();
        userEntity.ifPresent(userObject -> {
            if(userObject.getPlatform().equals("THIS")) {
                emailService.sendForgotPasswordMail(userEmail, forgotPwdToken);
                forgotPasswordRepository.save(new ForgotPasswordToken(userEmail, forgotPwdToken));
                success.set(true);
            }
        });

        return success.get();
    }

    /**
     * Method responsible for checking if token is valid and changing users password
     * @param forgotPasswordToken token received by email
     * @param newPassword new users' password
     * @return returns boolean value
     */

    public boolean changePassword(String forgotPasswordToken, String newPassword){
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        forgotPasswordRepository.findByForgotPasswordToken(forgotPasswordToken).ifPresent(token -> {
            if(token.getTokenExpiry().isAfter(LocalDateTime.now())){
                Optional<UserEntity> byEmail = Optional.ofNullable(this.getUserByEmail(token.getForgotPasswordEmail()));
                if(byEmail.isPresent()){
                    UserEntity userEntity = byEmail.get();
                    userEntity.setPassword(newPassword);
                    encodePassword(userEntity);
                    saveUser(userEntity);
                    forgotPasswordRepository.delete(token);
                    atomicBoolean.set(true);
                } else {
                    atomicBoolean.set(false);
                }
            }
        });

        return atomicBoolean.get();
    }

    /**
     *
     * @param email existing user email field
     * @return userEntity found by email field, if existing
     */

    private UserEntity getUserByEmail(String email){
        Optional<UserEntity> byEmail = userEntityRepository.findByEmail(email);
        return byEmail.orElse(null);
    }

    private void saveUser(UserEntity userEntity){
        userEntityRepository.save(userEntity);
    }

    /**
     *
     * @param userEntity userEntity during sign-up, for which user cart should be created
     */

    private void createCartForUser(UserEntity userEntity){
        CartEntity cartEntity = new CartEntity();
        userEntity.setCartEntity(cartEntity);
        cartEntity.setCartHolderEmail(userEntity.getEmail());
        cartEntityRepository.save(cartEntity);
    }


    /**
     *
     * @param userEntity userEntity object for checking
     * @return true if user already exists, false otherwise
     */

    private boolean userExists(UserEntity userEntity){
        return userEntityRepository.findByEmail(userEntity.getEmail()).isPresent()
                || userEntityRepository.findByUsername(userEntity.getUsername()).isPresent();
    }

    /**
     *
     * @param userEntity userEntity which is about to be set to active
     */

    private void setAccountEnabled(UserEntity userEntity){
        if(!userEntity.isEnabled()) {
            userEntity.setAccountEnabled(true);
            userEntityRepository.save(userEntity);
        }
    }


    /**
     * Method responsible for password hashing
     * @param userEntity userEntity which password will be encoded and saved in database
     */
    private void encodePassword(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    }

    private String encodePassword(String plainPassword){
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Method responsible for setting users role
     * @param userEntity userEntity, through this shop account making, every users role is set to be ROLE_USEr
     */
    private void setUserRole(UserEntity userEntity) {
        userEntity.setRole("ROLE_USER");
    }
}
