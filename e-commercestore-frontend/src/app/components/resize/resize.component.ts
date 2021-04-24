import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-resize',
  templateUrl: './resize.component.html',
  styleUrls: ['./resize.component.css']
})
export class ResizeComponent implements OnInit {

  private authenticated = false;
  private userEmail: string;
  private isAdmin: boolean;
  constructor(private securityService: SecurityService) { }

  ngOnInit() {

    let authObject = localStorage.getItem('AUTHENTICATED_USER');
      this.isAdmin = this.securityService.hasRole("ADMIN");
      console.log(this.isAdmin);
      if(authObject){
        this.userEmail = JSON.parse(authObject).email;
        this.authenticated = true;
      }
  }

}
