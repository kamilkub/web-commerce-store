import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/service/product/product.service';
import { Product } from 'src/app/model/product';
import { ActivatedRoute } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';
import { OrderStatus } from 'src/app/model/enums/order-status';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private isAuthenticated: boolean;
 
  constructor() { }

  ngOnInit() {
    
  }

}
