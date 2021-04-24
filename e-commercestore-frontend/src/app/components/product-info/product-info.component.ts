import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/service/cart/cart.service';
import { ProductService } from 'src/app/service/product/product.service';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.css']
})
export class ProductInfoComponent implements OnInit {

  private productExists: boolean = true;
  private productInfo: Product;
  private showMore = false;
  private isAuth: boolean;
  isDataAvailable = false;

  constructor(private cartService: CartService, private securityService: SecurityService, private productService: ProductService, private activeRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.getProductById();  
    this.isAuth = this.securityService.isUserLoggedIn();
  }


  getProductById(){
    const productId = this.activeRoute.snapshot.paramMap.get("id");
    this.productService.getProductById(productId.toString()).subscribe(data => {
      this.productInfo = data;
      this.isDataAvailable = true;
    }, error => {
      this.productExists = false;
    });

   
  }

  

  addToUserCart(id: number){
    this.cartService.addProductToCart(id);
    this.router.navigateByUrl("/cart-section");
  }



}
