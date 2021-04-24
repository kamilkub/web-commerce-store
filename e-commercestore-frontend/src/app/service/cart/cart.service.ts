import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Product } from 'src/app/model/product';
import { Observable, Subject } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { UserCartInfo } from 'src/app/model/user-cart-info';
import { CartPriceResponse } from 'src/app/model/cart-price-response';
@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl = "http://localhost:8080/cart";
  

  constructor(private httpClient: HttpClient) {}
  

  addProductToCart(productId: number){
      let param = new HttpParams();
      param = param.append('productId', productId.toString())
      console.log(param);
      this.httpClient.get(`${this.baseUrl}/add-product`, {params: param}).subscribe();
      this.getCurrentCartPrice();
      this.getProductsCount();
     
  }

  deleteProductFromCart(productId: number): boolean{
      let isDeleted = false;
      let params = new HttpParams();
      params = params.append("productId", productId.toString());
      this.httpClient.delete(`${this.baseUrl}/delete-product`, {params: params}).subscribe(response => {
          isDeleted = true;
      });

      return isDeleted;
  }

  getAllProductsFromUserCart(): Observable<Product[]>{
    return this.httpClient.get<Product[]>(`${this.baseUrl}/all-products`).pipe(map(response => response), delay(1000));
    
  }

  
  deleteAllProducts(){
    return this.httpClient.delete(`${this.baseUrl}/deleteCart-products`).subscribe();
  }

  getCurrentCartPrice(): Observable<CartPriceResponse> {
   return this.httpClient.get<CartPriceResponse>(`${this.baseUrl}/current-price`, {observe : 'response'})
      .pipe(delay(2000), map(data => data.body));
  }

  getProductsCount(): Observable<CartPriceResponse> {

    return this.httpClient.get<CartPriceResponse>(`${this.baseUrl}/product-count`, {observe : 'response'})
      .pipe(delay(2000), map(data => data.body));

  }
}
