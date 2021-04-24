import { SecurityContext } from '@angular/compiler/src/core';
import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SecurityService } from './security.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuardianService implements CanActivate {

  constructor(private router: Router, private securityService: SecurityService) { }
  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const userGuard = this.securityService.isUserLoggedIn();

    if(state.url.includes('admin-panel') && userGuard){
        if(this.securityService.hasRole('ADMIN'))
          return true;
        else
          return false;
    }
    
    
    if(userGuard){
      return true;
    }

    this.router.navigate(['/login'], {queryParams: {returnUrl : state.url }});
    
    return false;
   
  }
}
