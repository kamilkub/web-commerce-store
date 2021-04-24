import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/model/category';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  private categoriesList: Category[];
  categoryForm: FormGroup;
  private errorStatus = false;


  // Pagination - variables
  pageNumber = 1;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  constructor(private categoryService: CategoryService, private formBuilder: FormBuilder, private activeRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
      this.activeRoute.paramMap.subscribe(() => {
        this.getAllCategories();
        this.categoryForm = this.formBuilder.group({
          categoryName: ['', Validators.required]
        });
      })
      
  }

  removeCategory(categoryId: number){
    this.categoryService.deleteCategoryById(categoryId);
    this.categoriesList = this.categoriesList.filter((v, i, categoriesList) => v.id != categoryId);
  }

  addCategory(){
    this.categoryService.createCategory(this.categoryForm.get('categoryName').value).subscribe(response => {
        if(response.ok){
          this.getAllCategories();
          this.categoryForm.reset();
        } else {
            this.errorStatus = true;
        }
    });
  
  }

  searchRedirect(keyword: string){
    this.router.navigateByUrl(`/admin-panel/categories/${keyword}`);
  }

  getAllCategories(){
    const keywordSearch = this.activeRoute.snapshot.paramMap.get('keyword');

    if(keywordSearch){
      this.getCategoriesByName(keywordSearch);
    } else {
      this.categoryService.getAllCategoriesPagination(this.pageNumber - 1, this.pageSize).subscribe(this.transformData());
    }
    
  }

  transformData(){
    return data => {
      this.categoriesList = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
    }
  }

  getCategoriesByName(keyword: string){
      this.categoryService.getCategoriesByName(keyword, this.pageNumber - 1, this.pageSize)
          .subscribe(this.transformData());
  }

}
