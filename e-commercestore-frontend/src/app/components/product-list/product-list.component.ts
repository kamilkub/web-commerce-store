import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/service/product/product.service';
import { Product } from 'src/app/model/product';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from 'src/app/service/cart/cart.service';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  private shopProducts: Product[];
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;

  

  constructor(private securityService: SecurityService, private productService: ProductService, private route: ActivatedRoute, 
    private cartService: CartService, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.findProducts();
    });
  }


  findProducts(){
    const searchByKeyword = this.route.snapshot.paramMap.has('keyword');
    const searchByCategoryName = this.route.snapshot.paramMap.has('categoryName');
    if(searchByKeyword){
      this.findProductsByKeyWord();
    } else if(searchByCategoryName) {
      this.findProductsByCategoryName();
    } else {
      this.productService.getAllShopProducts(this.pageNumber - 1, this.pageSize)
          .subscribe(this.transformResult());
    }
    
  }

  findProductsByKeyWord(){
      const keyword = this.route.snapshot.paramMap.get("keyword");
      this.productService.getProductsByKeyword(this.pageNumber - 1, this.pageSize, keyword)
          .subscribe(this.transformResult());
  }

  findProductsByCategoryName(){
    const categoryName = this.route.snapshot.paramMap.get("categoryName");
    this.productService.getProductsByCategoryName(this.pageNumber - 1,this.pageSize, categoryName)
    .subscribe(this.transformResult());
  }

  addProductToCart(product: Product){
    this.cartService.addProductToCart(product.id);
    this.router.navigateByUrl("/cart-section");
  }


  transformResult(){
      return data => {
        this.shopProducts = data.content;
        this.pageNumber = data.number + 1;
        this.pageSize = data.size;
        this.totalPages = data.totalPages;
        this.totalElements = data.totalElements;
      }
  }

}
