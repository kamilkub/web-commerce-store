import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Order } from 'src/app/model/order';
import { OrderResponse } from 'src/app/response/order-response';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private orderUrl = "http://localhost:8080/order";

  constructor(private httpClient: HttpClient) { }


  createOrder(order: Order){
      this.httpClient.post(`${this.orderUrl}/create-order`, order)
          .subscribe(response => {
              console.log(response);
          });
  }

  getOrders(page: number, size: number): Observable<OrderResponse>{
    return this.httpClient.get<OrderResponse>(`${this.orderUrl}/orders?page=${page}&size=${size}`).pipe(map(response => response));
  }

  updateOrder(order: Order){
    this.httpClient.put(`${this.orderUrl}/update-order`, order).subscribe();
  }

  getOrderById(orderId: string): Observable<Order>{
    let httpParams = new HttpParams();
    httpParams = httpParams.append('orderId', orderId);
    return this.httpClient.get<Order>(`${this.orderUrl}/orders/byId`, {params: httpParams})
            .pipe(map(response => response));
  }

  removeOrder(orderId: number){
    let httpParams = new HttpParams();
    httpParams = httpParams.append('orderId', orderId.toString());
    this.httpClient.delete(`${this.orderUrl}/delete-order`, {params: httpParams}).subscribe();
  }

  findOrdersByUsername(page: number, size: number, username: string): Observable<OrderResponse>{
    const url = `${this.orderUrl}/orders-by-username?username=${username}&page=${page}&size=${size}`;

    return this.httpClient.get<OrderResponse>(url);
  }

  findUserOrders(): Observable<Order[]>{
    return this.httpClient.get<Order[]>(`${this.orderUrl}/user-orders`)
                .pipe(map(response => response));
  }

}
