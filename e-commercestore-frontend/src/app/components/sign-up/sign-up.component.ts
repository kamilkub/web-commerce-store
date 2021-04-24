import { AstMemoryEfficientTransformer } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  private isAccountCreated: boolean;
  private disableForm = false;
  signUpForm: FormGroup;

  constructor(private securityService: SecurityService, private router: Router, private formBuilder: FormBuilder) {
    
   }

  ngOnInit() {
    this.signUpForm = this.formBuilder.group({
      emailField: ['', Validators.compose([Validators.required, Validators.email])],
      username: ['', Validators.required],
      password: ['', Validators.compose([Validators.required, Validators.pattern("(?=.*?[A-Z])(?=.*?[0-9]).{8,}")])]
    });
      
    
    if(this.securityService.isUserLoggedIn()){
        this.router.navigateByUrl("/");
    }
    
  }

  registerUser(){
    const email = this.signUpForm.get('emailField').value;
    const username = this.signUpForm.get('username').value;
    const password = this.signUpForm.get('password').value;
      this.securityService.registerUser(email, username, password)
          .subscribe(data => {
              if(data.ok){
                this.isAccountCreated = true;
                this.disableForm = true;
              } else {
                this.isAccountCreated = false;
                this.disableForm = false;
              }
          }, error => {this.isAccountCreated = false;
                       this.disableForm = false; } );
    
  }

}
