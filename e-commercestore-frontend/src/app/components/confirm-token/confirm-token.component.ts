import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-confirm-token',
  templateUrl: './confirm-token.component.html',
  styleUrls: ['./confirm-token.component.css']
})
export class ConfirmTokenComponent implements OnInit {

  tokenInfo: string;
  infoStyle: string;

  constructor(private securityService: SecurityService, 
    private activeRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
      if(this.activeRoute.snapshot.paramMap.has("token")){
          this.confirmUserToken();
      } else {
        this.tokenInfo = "Sorry, your token must be empty";
        this.infoStyle = "alert-danger";
      }
  }

  confirmUserToken(){
    const token = this.activeRoute.snapshot.paramMap.get("token");
    
    this.securityService.confirmToken(token)
        .subscribe(data => {
          if(data.ok){
            this.tokenInfo = "Now you can log into your account! You will be redirected";
            this.infoStyle = "alert-success";
            setTimeout(() => this.router.navigateByUrl("/login"), 3000);
          } else {
            this.tokenInfo = "Ups, something went wrong with authenticating your token :("
            this.infoStyle = "alert-danger";  
          }
        });

    }


}
