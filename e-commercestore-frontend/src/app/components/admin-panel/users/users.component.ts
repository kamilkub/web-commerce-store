import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/users/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {


  usersList: User[];

  pageNumber = 1;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  constructor(private userService: UserService, private activateRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.activateRoute.paramMap.subscribe(() => {
      this.getAllUsers();
    });
 
  }

  getAllUsers(){
    const keywordSearch = this.activateRoute.snapshot.paramMap.get('keyword');

    if(keywordSearch){
      this.getUsersByEmail(keywordSearch);
    }  else {
      this.userService.findAllUsers(this.pageNumber - 1, this.pageSize).subscribe(this.transformData());
    } 
  }

  searchRedirect(value: string){
      this.router.navigateByUrl(`/admin-panel/users/${value}`);
  }

  getUsersByEmail(email: string){
    console.log('Called');
    
      this.userService.findUsersByEmail(email, this.pageNumber - 1, this.pageSize)
          .subscribe(this.transformData());
  }

  removeUser(userId: number){
    this.usersList = this.usersList.filter((v, i, usersList) => v.id != userId);
    this.userService.deleteUserById(userId);
  }

  transformData(){
    return data => {
      this.usersList = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalPages = data.totalPages;
      this.totalElements = data.totalElements;
    }
  }

}
