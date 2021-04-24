import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/service/product/product.service';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  constructor(private securityService: SecurityService, private router: Router){

  }
  ngOnInit() {
  
  }

  signOut(){
    this.securityService.logout();
    this.router.navigateByUrl("/");
  }

 
}
