import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { StripeSessionToken } from 'src/app/model/stripe-session-token';
import { CartService } from 'src/app/service/cart/cart.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  private cartPrice;
  private productCount;
  private StripeInstance;
  checkoutForm: FormGroup;
  private loadingScreen = false;
  private feePrice = 3;
 
  constructor(private httpClient: HttpClient, private route: Router, private cartService: CartService
      , private formBuilder: FormBuilder, private orderService: OrderService) { }

   async ngOnInit() {
    // this.cartService.getProductsCount().subscribe(data => {
    //     if(data.body.cartCount <= 0){
    //       this.route.navigateByUrl("/cart-section");
    //     } 
    // });

    this.cartService.getCurrentCartPrice()
        .subscribe(data => this.cartPrice = data.cartPrice);
    this.cartService.getProductsCount()
        .subscribe(data => this.productCount = data.cartCount);


    
      this.checkoutForm = this.formBuilder.group({
        customerFirstName: [''],
        customerLastName: [''],
        customerEmail: [''],
        orderCountry: [''],
        orderStreet: [''],
        orderCity: [''],
        orderRegion: [''],
        orderPostalCode: [''],
        });

      this.StripeInstance = await loadStripe('');
   

  }

  
  createOrder(){
    this.loadingScreen = true;

    let orderObject = new Order();
    orderObject.buyerEmail = this.customerEmail.value;
    orderObject.buyerFirstName = this.customerFirstName.value;
    orderObject.buyerSecondName = this.customerLastName.value;
    orderObject.shippingCity = this.orderCity.value;
    orderObject.shippingCountry = this.orderCountry.value;
    orderObject.shippingPostalCode = this.orderPostalCode.value;
    orderObject.shippingStreet = this.orderStreet.value;
    orderObject.shippingRegion = this.orderRegion.value;

    this.orderService.createOrder(orderObject);
    
    this.createPayment();
  }


  async createPayment(){


   
    this.httpClient.get<StripeSessionToken>('http://localhost:8080/payment/stripe/session-id', { observe: 'response' })
      .subscribe(data => {
        if (data.ok)
          this.StripeInstance.redirectToCheckout({ sessionId: data.body.sessionID });
        else {
          alert('Ups, something went wrong. Try again later!');
        }

      });
     
  }
   
  get customerEmail(){
    return this.checkoutForm.get('customerEmail');
  }

  get customerLastName(){
    return this.checkoutForm.get('customerLastName');
  }

  get customerFirstName(){
    return this.checkoutForm.get('customerFirstName');
  }

  get orderCountry(){
    return this.checkoutForm.get('orderCountry');
  }

  get orderStreet(){
    return this.checkoutForm.get('orderStreet');
  }


get orderCity(){
  return this.checkoutForm.get('orderCity');
}

get orderRegion(){
  return this.checkoutForm.get('orderRegion');
}

get orderPostalCode(){
  return this.checkoutForm.get('orderPostalCode');
}

}
