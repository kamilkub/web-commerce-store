import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError,  } from 'rxjs/operators';
import { SecurityService } from '../security.service';


@Injectable()
export class ErrorResponseInterceptorService implements HttpInterceptor {

  constructor(private router: Router, private securityService: SecurityService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    
    if(this.securityService.isUserLoggedIn()){
      req = req.clone({
        headers: new HttpHeaders({
          // 'Content-Type': 'application/json',
          'Authorization': 'Basic ' + this.securityService.createBasicAuthTokenFromUserFields(),
          'Access-Control-Allow-Origin': 'http://localhost:4200'
        })
      });

    } 


    return next.handle(req);

      
    }

  }


