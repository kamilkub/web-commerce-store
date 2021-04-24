import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order/order.service';

@Component({
  selector: 'app-order-tracking',
  templateUrl: './order-tracking.component.html',
  styleUrls: ['./order-tracking.component.css']
})
export class OrderTrackingComponent implements OnInit {

  userOrders: Order[];
  isOrderFinished: boolean = false;

  constructor(private orderService: OrderService, private activeRoute: ActivatedRoute) { }

  ngOnInit() {
    this.isOrderFinished = this.activeRoute.snapshot.paramMap.has('orderProcess');
    this.getUsersOrders();
  }



  getUsersOrders(){
      this.orderService.findUserOrders()
          .subscribe(data => this.userOrders = data);
  }

}
