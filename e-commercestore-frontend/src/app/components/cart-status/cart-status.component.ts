import { Component, OnInit } from '@angular/core';
import { timeoutWith } from 'rxjs/operators';
import { CartService } from 'src/app/service/cart/cart.service';
import { SecurityService } from 'src/app/service/security/security.service';
import { CartComponent } from '../cart/cart.component';

@Component({
  selector: 'app-cart-status',
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.css']
})
export class CartStatusComponent implements OnInit {

  private isAuthenticated: boolean;
  private productCount;
  private cartPrice;
  constructor(private securityService: SecurityService) { }

  ngOnInit() {
    this.isAuthenticated = 
              this.securityService.isUserLoggedIn();
              
    }

}
