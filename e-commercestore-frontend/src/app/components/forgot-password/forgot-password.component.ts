import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecurityService } from 'src/app/service/security/security.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  private isValid: boolean = true;
  private isChanged: boolean = false;

  constructor(private securityService: SecurityService, private route: ActivatedRoute
             , private router: Router) { }

  ngOnInit() {

  }

  changePassword(newPassword: string, confirmPwd: string){
    if(newPassword === confirmPwd){
      const token = this.route.snapshot.paramMap.get("token");
      this.securityService.changeForgotPassword(token, newPassword)
          .subscribe(data => {
            console.log(data);
            if(data.ok){
              this.isValid = true;
              this.isChanged = true;
              setTimeout(() => {
                this.router.navigate(['/login']);
              }, 3000);
            } else {
              this.isValid = false;
            }
          });
    } else {
      this.isValid = false;
    }
  }



}
