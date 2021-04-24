import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VirtualTimeScheduler } from 'rxjs';
import { Category } from 'src/app/model/category';
import { Product } from 'src/app/model/product';
import { CategoryService } from 'src/app/service/category/category.service';
import { ProductService } from 'src/app/service/product/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  product: Product;
  errorTrue = false;
  success = false;
  editProductForm: FormGroup;
  productImage: File;

  constructor(private productService: ProductService, private activeRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    if(this.activeRoute.snapshot.paramMap.has("id"))
        this.findProduct();
      else
        this.router.navigateByUrl('/pageNotFound');
  }

  
  findProduct(){
    const id = this.activeRoute.snapshot.paramMap.get("id");
    this.productService.getProductById(id).subscribe(data => {
      this.product = data;
    });
  }


  updateProduct(){
    if(this.product.imageUrl == 'changed'){
      console.log('yep');
      this.productService.updateProductWithImageUpdate(this.product, this.productImage);
    } else {
      
      this.productService.updateProduct(this.product).subscribe(response => {
        console.log(response);
        this.router.navigateByUrl("/admin-panel/products");
      });
    }

      
  }

  setActive(value: string){
    if(value == "true"){
      this.product.isActive = true;
      console.log(this.product.isActive);
      
    } else {
      this.product.isActive = false;
    }
  
  }

  setDescription(value: string){
    this.product.description = value;
  }

  setProductName(value: string){
    this.product.name = value;
  }

  setQuantity(value: number){
    this.product.unitsInStock = value;
  }

  setPrice(value: number){
    this.product.price = value;
  }

  setProductImage(value: FileList){
      this.productImage = value.item(0);
      this.product.imageUrl = "changed";
  }



}
