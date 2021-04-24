import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/service/cart/cart.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  
  private cartProducts: Product[];

  productsCount: Number = 0;
  cartPrice: Number = 0;

  constructor(private cartService: CartService,) { }

  
   ngOnInit() {
      this.getUserCartContent();
   

      this.cartService.getCurrentCartPrice()
          .subscribe(data =>{ this.cartPrice = data.cartPrice});
      this.cartService.getProductsCount()
          .subscribe(data => this.productsCount = data.cartCount);

    
          
  }


  getUserCartContent(){
    this.cartService.getAllProductsFromUserCart().subscribe(data => {
      this.cartProducts = data;
    });
  }

  removeProductFromCart(productId: number){
    this.cartProducts = this.cartProducts.filter((v, i, arr) => v.id != productId);
    this.cartService.deleteProductFromCart(productId);
  }

  getProductsCount(){
    return this.productsCount;
  }


  

}
