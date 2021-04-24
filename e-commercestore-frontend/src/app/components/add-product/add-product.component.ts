import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/model/category';
import { Product } from 'src/app/model/product';
import { CategoryService } from 'src/app/service/category/category.service';
import { ProductService } from 'src/app/service/product/product.service';


@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  private categoriesForAdd: Category[];
  private imageFileUp: File;
  private productCategory: Category;
  private isAdded = false;

  constructor(private categoryService: CategoryService, private productService: ProductService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.getProductCategories();
  }


  getProductCategories(){
    this.categoryService.getAllCategories().subscribe(data => {
      this.categoriesForAdd = data;
    });
   
  }

  getCategory(category: Category){
      this.productCategory = category;
  }

  getImage(imageFile: FileList){
      this.imageFileUp = imageFile.item(0);
  }

  async addProductToShop(name: string, price: number, 
                    quantity: number, description: string, categoryId: number){

    // if(name != undefined)
   const category = this.categoriesForAdd.find(category => category.id == categoryId);    
           
    let product = new Product();
    product.name = name;
    product.description = description;
    product.price = price;
    product.unitsInStock = quantity;
    product.category = category;
    product.isActive = true;
    
    this.productService.addProductToShop(product, this.imageFileUp);
   
             
  }



}
