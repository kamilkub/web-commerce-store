import { Component, NgZone, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.css']
})
export class HeaderBarComponent implements OnInit {

  private isLoggedIn: boolean;
  private isAdmin = false;
  private authenticated = false;
   userEmail;

  constructor(private securityService: SecurityService, private router: Router) { }

  ngOnInit() {
      this.isLoggedIn = this.securityService.isUserLoggedIn();
      this.isAdmin = this.securityService.hasRole("ADMIN");

      let authObject = localStorage.getItem('AUTHENTICATED_USER');
    
      if(authObject){
        this.userEmail = JSON.parse(authObject).email;
        this.authenticated = true;
      }
  }


  logout(){
      this.securityService.logout();
      document.location.href = "http://localhost:4200/";
  }


}
