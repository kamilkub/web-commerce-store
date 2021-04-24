import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from 'src/app/model/category';
import { map } from 'rxjs/operators';
import { CategoryResponse } from 'src/app/response/category-response';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }


  getAllCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>('http://localhost:8080/products/all-categories')
      .pipe(map(response => response));
  }

  getAllCategoriesPagination(page: number, size: number): Observable<CategoryResponse>{
    const url = `http://localhost:8080/products/all-categories-pag?page=${page}&size=${size}`;
    return this.httpClient.get<CategoryResponse>(url).pipe(map(response => response));
  }

  getCategoriesByName(name: string, page: number, size: number): Observable<CategoryResponse> {
    const url = `http://localhost:8080/products/categories-by-name?categoryName=${name}&page=${page}&size=${size}`;
    return this.httpClient.get<CategoryResponse>(url).pipe(map(response => response));
  }


  deleteCategoryById(categoryId: number){
    let params = new HttpParams();
    params = params.append('categoryId', categoryId.toString());
    this.httpClient.delete('http://localhost:8080/admin-panel/delete-category', {params: params}).subscribe();
  }

  createCategory(categoryName: string){
    let category = new Category();
    category.categoryName = categoryName;

    return this.httpClient.post('http://localhost:8080/admin-panel/add-category', category, {observe: 'response'}).pipe(map(response => response));
  }
}
