import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.css']
})
export class SearchInputComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  searchForProductsByName(productName: String){
      this.router.navigateByUrl(`/search/${productName}`);
  }

}
