import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';
declare var FB: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private isLoginValid: boolean = true;
  private isForgotPasswordMode: boolean = false;
  private validationInfo: string;
  private password: string;
  private redirectUrl = "/";
  private successMessage: string;
  private forgotIsValid = false;
 
  

  constructor(private securityService: SecurityService, private router: Router, 
              private route: ActivatedRoute, private ngZone: NgZone) { }

  ngOnInit() {
    this.loadUpFacebookSDK();
    const returnUrl = this.route.snapshot.paramMap.has("returnUrl");

    if(returnUrl){
      this.redirectUrl = this.route.snapshot.paramMap.get("returnUrl");
    }

    if(this.securityService.isUserLoggedIn()){
        this.router.navigateByUrl(this.redirectUrl);
    }

  }


  loadUpFacebookSDK(){
    (window as any).fbAsyncInit = function() {
      FB.init({
        appId      : '',
        cookie     : true,
        xfbml      : true,
        version    : 'v8.0'
      });
        
      FB.AppEvents.logPageView();   
        
    };
  
    (function(d, s, id){
       var js, fjs = d.getElementsByTagName(s)[0];
       if (d.getElementById(id)) {return;}
       js = d.createElement(s); js.id = id;
       js.src = "https://connect.facebook.net/en_US/sdk.js";
       fjs.parentNode.insertBefore(js, fjs);
     }(document, 'script', 'facebook-jssdk'));
  }

  signFacebookUserInit(){
    FB.login(() => {
      FB.api('/me?fields=name,email', (response) => {
          this.securityService.authenticateFacebookUser(response.id, response.name, response.email)
            .subscribe(data => {
              if(data.ok){
                this.ngZone.run(() => this.router.navigateByUrl(this.redirectUrl));
              } else {
                alert('Something went wrong try again later')
              }
           
            }, error => alert('You  already have an account on our platform! Try typing in your credentials'));
      
        });
      
      }, {scope: 'email'});
  }


  
  signFacebookUser(){
    FB.getLoginStatus((response) => {
         this.signFacebookUserInit();
    });
  }

  
  signInUser(username: string, password: string){
    this.securityService.authenticateUser(username, password).subscribe((data) => {
      console.log(data);
      this.isLoginValid = true;
      if(data.includes("ADMIN")){
        this.router.navigateByUrl("/admin-panel");
      } else {
        this.router.navigateByUrl(this.redirectUrl);
      }
    }, () => {
      this.validationInfo = "Bad username or password";
      this.isLoginValid = false;
      this.password = "";
    })
  }

  setPwd(value: string){
    this.password = value;
  }

  setForgotPasswordMode(){
    this.isForgotPasswordMode = true;
  }

  sendChangePasswordLink(email: string){
    this.isLoginValid = true;
    if(this.isForgotPasswordMode && this.validateEmail(email)){
      this.securityService.sendForgotPwdLink(email).subscribe(data =>{
        if(data.ok){
          this.forgotIsValid = true;
          this.successMessage = "Changing password instructions has been sent on your email. Thank you!";
          setTimeout(() => this.redirectToEmailBox(email), 3000);
        } 

      }, error => {
        this.isLoginValid = false;
        this.validationInfo = "Provided email must be wrong";
      });
      
    }
   
  }

  validateEmail(value: string){
    return value.replace(/\s/g,"") != "" && value.includes("@");
  }

  redirectToEmailBox(email: string){
      const emailDomain = email.substr(email.indexOf('@'), email.length);
      document.location.href = "https://" + emailDomain;
  }


}
