import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/model/product';
import { delay, map } from 'rxjs/operators';
import { ProductResponse } from 'src/app/response/product-response';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = 'http://localhost:8080/products'
 
  constructor(private httpClient: HttpClient) { }


  getAllShopProducts(page: number, size: number): Observable<ProductResponse> {
    let paginationParams = new HttpParams();
    paginationParams = paginationParams.append("page", page.toString());
    paginationParams = paginationParams.append("size", size.toString());

   return this.httpClient.get<ProductResponse>(this.productsUrl + '/all-products', {params: paginationParams})
          .pipe(map(response => response));
  }

  getProductsByCategoryName(page: number, size: number, categoryName: string): Observable<ProductResponse> {
    let params = new HttpParams();
    params = params.append('categoryName', categoryName);
    params = params.append("page", page.toString());
    params = params.append("size", size.toString());

    return this.httpClient.get<ProductResponse>(`${this.productsUrl}/products-by-category`, {params: params})
    .pipe(map(response => response), delay(100));
           
  }

  async addProductToShop(product: Product, fileImage: File){
     let fileReader = new FileReader();

     if(product == null || fileImage == undefined){
        return false;
     }

     fileReader.readAsDataURL(fileImage);

    fileReader.onload = () => {
      
    this.httpClient.post<HttpResponse<Object>>(`http://localhost:8080/admin-panel/add-product`, {productInfo: product ,productImage: {imageBase64: fileReader.result, fileName: fileImage.name}}, {
        headers: {
            'Content-Type' : undefined
        },
        observe: 'response'
    }).subscribe(response => {
      if(response.ok){
        document.location.href = "http://localhost:4200/admin-panel/products";
        return true;
      } else {
        return false;
      }
    });

    }
   
  }

  getProductsByKeyword(page: number, size: number, keyword: string): Observable<ProductResponse> {
    let params = new HttpParams();
    params = params.append("productName", keyword);
    params = params.append("page", page.toString());
    params = params.append("size", size.toString());

    return this.httpClient.get<ProductResponse>(`${this.productsUrl}/products-by-name`, {params: params})
      .pipe(map(response => response));

  }

  getProductById(id: string) : Observable<Product>{
    let params = new HttpParams();
    params = params.append('productId', id);

    return this.httpClient.get<Product>(`${this.productsUrl}/product-by-id`, {params: params})
    .pipe(map(response => response));
  }

  removeProduct(productId: number){
    let productIdParam = new HttpParams();
    productIdParam = productIdParam.append('productId', productId.toString());
    this.httpClient.delete(`http://localhost:8080/admin-panel/delete-product`, {params: productIdParam}).subscribe(response => {
      console.log(response);
    });
  }

  updateProduct(product: Product){
    
      return this.httpClient.put(`http://localhost:8080/admin-panel/edit-product`, {productInfo: product, productImage: null }, {observe: 'response'})
      .pipe(map(response => response));
    
  }

  updateProductWithImageUpdate(product: Product, fileImage: File){
    
        let fileReader = new FileReader();
        fileReader.readAsDataURL(fileImage);

        fileReader.onload = () => {

          return this.httpClient.put(`http://localhost:8080/admin-panel/edit-product`, {
            productInfo: product, 
            productImage: 
                  {imageBase64: fileReader.result, 
                    fileName: fileImage.name} }, {observe: 'response'})
          .subscribe(response => {
            if(response.ok){
              document.location.href = "http://localhost:4200/admin-panel/products";
            }
          });

        }
    
  }

  


}