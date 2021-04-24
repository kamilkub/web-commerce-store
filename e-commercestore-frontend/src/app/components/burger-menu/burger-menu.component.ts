import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/model/category';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-burger-menu',
  templateUrl: './burger-menu.component.html',
  styleUrls: ['./burger-menu.component.css']
})
export class BurgerMenuComponent implements OnInit {

  private categories: Category[];
  private isLoggedIn: boolean = false;

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.getAllCategories();
    if(localStorage.getItem("AUTHENTICATED_USER")){
      this.isLoggedIn = true;
    }
  }

  getAllCategories(){
    this.categoryService.getAllCategories().subscribe(data => {
      this.categories = data;
    });

  }

}
