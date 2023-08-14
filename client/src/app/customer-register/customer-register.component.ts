import { Component, OnInit } from '@angular/core';
import RegisterUser from '../models/RegisterUser';
import { UserService } from '../services/user/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css']
})
export class CustomerRegisterComponent implements OnInit {
  user:RegisterUser = new RegisterUser("","","","");
  error:string = "";

  constructor(private userService:UserService, private router:Router, private toastr: ToastrService) {

  }

  ngOnInit(): void {

  }

  onSubmit() {
    const passwordRegex =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])/;
    if(!passwordRegex.test(this.user.password)) {
      this.toastr.warning("Enter a valid password");
    }
    else {
      this.userService.customerRegister(this.user).subscribe(
        (response:any)=> {
          if(response.success) {
            this.toastr.success('Registration successfull');
            this.router.navigate(['customer-login']);
          }
        },
        (error:any)=> {
          this.error = error.error;
        }
      )
    }
  }

}
