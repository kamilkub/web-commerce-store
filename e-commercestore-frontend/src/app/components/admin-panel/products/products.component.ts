import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/service/product/product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  private products: Product[];


  // Pagination - variables
  pageNumber = 1;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  constructor(private productService: ProductService, private router: Router, private active: ActivatedRoute) { }

  ngOnInit() {
    this.active.paramMap.subscribe(() => {
      this.getAllProducts();
    });
  }


  getAllProducts(){
    const keywordSearch = this.active.snapshot.paramMap.get('keyword');

    if(keywordSearch){
      this.findProductsByKeyword(keywordSearch);
    }  else {
      this.productService.getAllShopProducts(this.pageNumber - 1, this.pageSize)
        .subscribe(this.transformData());
    }  
  }

  findProductsRedirect(keyword: string){
    this.router.navigateByUrl(`/admin-panel/products/${keyword}`)
  }

  findProductsByKeyword(keyword: string){
    console.log("Called");
    
    this.productService.getProductsByKeyword(this.pageNumber - 1, this.pageSize, keyword)
        .subscribe(this.transformData())
  }

  removeProduct(productId: number){
      this.products = this.products.filter((v, i, products) => v.id != productId);
      this.productService.removeProduct(productId);
  }

  redirectToAdding(){
      this.router.navigate(['/admin-panel/addProduct'])
  }

  transformData(){
    return data => {
      this.products = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
    }
  }


}
