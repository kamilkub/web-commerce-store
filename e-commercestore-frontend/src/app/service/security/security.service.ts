import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/internal/operators/map';
import { User } from 'src/app/model/user';
import { Authentication } from './authentication';


@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  authenticationDetails = new Authentication();
  private authenticationUrl = "http://localhost:8080/user"

  constructor(private httpClient: HttpClient, private router: Router) {
   }

   // login user
  authenticateUser(username: string, password: string){
      return this.httpClient.get<Authentication>(`${this.authenticationUrl}/sign-in`,{ 
        headers: { 
              authorization: this.createBasicAuthToken(username, password) 
      }}).pipe(map(response => {
          this.authenticationDetails = response;
          this.authenticationDetails.password = password;
          localStorage.setItem('AUTHENTICATED_USER', JSON.stringify(this.authenticationDetails));
          return this.authenticationDetails.role;
      }));

  }

  authenticateFacebookUser(facebookId: string, facebookName: string, facebookEmail: string){
    return this.httpClient.post<Authentication>(`${this.authenticationUrl}/facebook-user`, {
      name: facebookName,
      email: facebookEmail,
      id: facebookId
    }, {observe: 'response'}).pipe(map(response => {
      if(response.ok){
        this.authenticationDetails = response.body;
        this.authenticationDetails.password = facebookId;
        localStorage.setItem('AUTHENTICATED_USER', JSON.stringify(this.authenticationDetails));
      }
        
      return response;
    }));
  }


  createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ":" + password);
  }

  createBasicAuthTokenFromUserFields(){
    const authObject = JSON.parse(localStorage.getItem('AUTHENTICATED_USER'));
    return window.btoa(authObject.username + ":" + authObject.password);
  }

  isUserLoggedIn(){
    const isLoggedIn = localStorage.getItem('AUTHENTICATED_USER');

    if(isLoggedIn){
      return true;
    }
    return false;
  }

  getPassword(){
    return this.authenticationDetails.password;
  }

  getUsername(){
    return this.authenticationDetails.username;
  }

  logout(){
    localStorage.removeItem('AUTHENTICATED_USER');
    this.authenticationDetails = new Authentication();
  }


  hasRole(role: string) : boolean{
    const userData = JSON.parse(localStorage.getItem('AUTHENTICATED_USER'));
    if(userData == null) return false;

    if(userData.role)
      return userData.role.includes(role);
    else
      return false;
 
  }


  // register user
  registerUser(email: String, username: String, password: String){
      const signUpUser = new User();
      signUpUser.setEmail(email);
      signUpUser.setUsername(username);
      signUpUser.setPassword(password);

      return this.httpClient.post(`${this.authenticationUrl}/sign-up`, signUpUser, {observe: 'response'})
          .pipe(map(response => response));
 
  }

  confirmToken(token: string){
      return this.httpClient.post(`${this.authenticationUrl}/token-activation`, {token: token}, {observe: 'response'})
          .pipe(map(response => response));

  }

  changeForgotPassword(token: string, newPassword: string) {
     return this.httpClient.post(`${this.authenticationUrl}/change-password`, {token: token, password: newPassword}, {observe: 'response'})
          .pipe(map(response => response));
          
  }

  sendForgotPwdLink(email: string) {
    
    return this.httpClient.post(`${this.authenticationUrl}/forgot-password`, {email: email}, {observe: 'response'})
        .pipe(map(response => response));  
  }
}
