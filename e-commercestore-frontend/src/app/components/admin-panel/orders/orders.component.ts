import { Component, OnInit } from '@angular/core';
import { MatGridTileHeaderCssMatStyler } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  ordersArray: Order[];
  dataAvailable = false;


  // Pagination - variables
  pageNumber = 1;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;


  constructor(private orderService: OrderService, private activate: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.activate.paramMap.subscribe(() => {
      this.findOrders();
    });
     
  }

  findOrders() {
    const keywordSearch = this.activate.snapshot.paramMap.get('keyword');

    if(keywordSearch){
      this.findOrdersByUsername(keywordSearch);
    } else {
      this.getOrders();
    }
  }

  findOrdersByUsernameRedirect(username: string){
    this.router.navigateByUrl(`/admin-panel/orders/${username}`)
  }

  findOrdersByUsername(username: string){
      this.orderService.findOrdersByUsername(this.pageNumber - 1, this.pageSize, username)
          .subscribe(this.transformData());
  }


  removeOrder(orderId: number){
      this.ordersArray = this.ordersArray.filter((v, i, orders) => v.id != orderId);
      this.orderService.removeOrder(orderId);
  }

  getOrders(){
    this.orderService.getOrders(this.pageNumber - 1, this.pageSize).subscribe(this.transformData());
  }

  transformData(){
    return data => {
      this.ordersArray = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
      this.dataAvailable = true;
    }
  }

}
