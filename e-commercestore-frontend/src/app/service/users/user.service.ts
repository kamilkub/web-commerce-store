import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from 'src/app/model/user';
import { UserResponse } from 'src/app/response/user-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/admin-panel/users';

  constructor(private httpClient: HttpClient) { }

  findAllUsers(page: number, size: number): Observable<UserResponse> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.httpClient.get<UserResponse>(url).pipe(map(response => response));
  }

  findUsersByEmail(email: string, page: number, size: number): Observable<UserResponse> {
    const url = `http://localhost:8080/admin-panel/users-by-email?email=${email}&page=${page}&size=${size}`;

    return this.httpClient.get<UserResponse>(url).pipe(map(response => response));
  }

  deleteUserById(userId: number){
    let params = new HttpParams();
    params = params.append('userId', userId.toString());
    this.httpClient.delete(this.baseUrl, {params: params}).subscribe();
  }

  getUserById(userId: string): Observable<User>{
    let params = new HttpParams();
    params = params.append('userId', userId);

    return this.httpClient.get<User>(`${this.baseUrl}/byId`, {params: params})
          .pipe(map(response => response));
  }

}
