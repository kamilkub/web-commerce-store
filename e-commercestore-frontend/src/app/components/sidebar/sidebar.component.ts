import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/model/category';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  private categories: Category[];
  private userEmail; string;
  private authenticated = false;
  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
      this.getAllCategories();


  }


  getAllCategories(){
    this.categoryService.getAllCategories().subscribe(data => {
      this.categories = data;
    })

  }

}
