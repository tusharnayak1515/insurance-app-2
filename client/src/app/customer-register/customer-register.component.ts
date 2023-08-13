import { Component, OnInit } from '@angular/core';
import RegisterUser from '../models/RegisterUser';
import { UserService } from '../services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css']
})
export class CustomerRegisterComponent implements OnInit {
  user:RegisterUser = new RegisterUser("","","","");
  error:string = "";

  constructor(private userService:UserService, private router:Router) {

  }

  ngOnInit(): void {

  }

  onSubmit() {
    this.userService.customerRegister(this.user).subscribe(
      (response:any)=> {
        if(response.success) {
          this.router.navigate(['customer-login']);
        }
      },
      (error:any)=> {
        this.error = error.error;
      }
    )
  }

}
