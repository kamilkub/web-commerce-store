import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderStatus } from 'src/app/model/enums/order-status';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order/order.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css']
})
export class EditOrderComponent implements OnInit {

  orderForm: FormGroup;
  order: Order;
  statusArray = [OrderStatus.PENDING, OrderStatus.ACCEPTED, OrderStatus.ON_THE_WAY, OrderStatus.DELIEVERED];

  constructor(private orderService: OrderService, private formBuilder: FormBuilder,
                  private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    const orderId = this.route.snapshot.paramMap.get('id');

    this.orderService.getOrderById(orderId).subscribe(data => {
        this.order = data;
        this.orderForm = this.formBuilder.group({
          buyerFirstName: [data.buyerFirstName, Validators.required],
          buyerSecondName: [data.buyerSecondName, Validators.required],
          buyerEmail:[data.buyerEmail, Validators.required] ,
          shippingCountry:[data.shippingCountry, Validators.required],
          shippingStreet: [data.shippingStreet, Validators.required],
          shippingCity: [data.shippingCity, Validators.required],
          shippingRegion: [data.shippingRegion, Validators.required],
          shippingPostalCode: [data.shippingPostalCode, Validators.required]
        });
    });
    
   
  }


  updateOrder(){
    this.order.buyerFirstName = this.orderForm.get('buyerFirstName').value;
    this.order.buyerSecondName = this.orderForm.get('buyerSecondName').value;
    this.order.buyerEmail = this.orderForm.get('buyerEmail').value;
    this.order.shippingCountry = this.orderForm.get('shippingCountry').value;
    this.order.shippingStreet = this.orderForm.get('shippingStreet').value;
    this.order.shippingCity = this.orderForm.get('shippingCity').value;
    this.order.shippingRegion = this.orderForm.get('shippingRegion').value;
    this.order.shippingCity = this.orderForm.get('shippingCity').value;
  

    this.orderService.updateOrder(this.order);

    
    this.router.navigateByUrl('/admin-panel/orders');
  }

  changeOrderState(orderStatus: number){
    for(let status of this.statusArray){
        if(orderStatus == status){
          this.order.orderStatus = status;
        }
    }
  }

}

