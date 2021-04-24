package org.store.ecommercestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.store.ecommercestore.mapper.*;
import org.store.ecommercestore.model.UserEntity;
import org.store.ecommercestore.service.AuthenticationService;
import org.store.ecommercestore.service.EmailService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin("localhost:4200")
public class UserAuthenticatorController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public Object signUp(@RequestBody @Valid UserEntity userEntity, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST, false, errors), HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString(); userEntity.setToken(token);

        if(authenticationService.signUp(userEntity)){
            emailService.sendActivationTokenMail(userEntity.getEmail(), token);
            return ResponseEntity.ok(new Response("User has been signed-up", HttpStatus.OK, true));
        }

        return new ResponseEntity<>("Try other credentials, username or email probably is taken", HttpStatus.CONFLICT);
    }

    @PostMapping("/facebook-user")
    public Object getFacebookUserDetails(@RequestBody FacebookUserData facebookUserData){
        return authenticationService.facebookRegister(facebookUserData);
    }

    @GetMapping("/sign-in")
    public Object signIn(){
        return authenticationService.signIn();
    }

    @PostMapping("/token-activation")
    public Object activateUserAccount(@RequestBody ConfirmToken token){
        if(authenticationService.activateUserAccount(token.getToken())){
            return ResponseEntity.ok(new Response("User account activated", HttpStatus.OK, true));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody @Valid ForgotPasswordMapper emailObject, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST, "This email is incorrect or empty"), HttpStatus.BAD_REQUEST);
        if(authenticationService.changePasswordLink(emailObject))
            return new ResponseEntity<>(new Response(HttpStatus.OK, "Forgot password link has been sent"), HttpStatus.OK);

        return new ResponseEntity<>("Something went wrong, maybe you provided mistaken email", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    public Object changePassword(@RequestBody ChangePasswordMapper changePasswordMapper) {
        if(authenticationService.changePassword(changePasswordMapper.getToken(), changePasswordMapper.getPassword())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.badRequest();
    }


}
