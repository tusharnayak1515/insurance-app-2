import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user/user.service';
import { NavigationStart, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string = "";
  password: string = "";
  error: string = "";
  showRegisterBtn: boolean = false;

  constructor(
      private userService: UserService, 
      private router: Router, 
      private cookieService: CookieService, 
      private toastr: ToastrService
    ) {

  }

  ngOnInit(): void {
    if (this.router.url.includes("customer")) {
      this.showRegisterBtn = true;
    }
    else {
      this.showRegisterBtn = false;
    }
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.showRegisterBtn =
          event.url.includes('customer') ? true : false;
      }
    });
  }

  onSubmit() {
    this.userService.userLogin(this.email, this.password).subscribe(
      (response: any) => {
        if (response?.success) {
          this.setAuthCookie(response?.token);
          console.log(response?.user);
          localStorage.setItem("user_details", JSON.stringify(response?.user));
          this.userService.setUser(response?.user);
          this.userService.setLoggedIn(true);
          this.error = "";
          this.email = "";
          this.password = "";
          if (response?.user?.role === "admin") {
            this.router.navigate(['admin', 'dashboard']);
          }
          else {
            this.router.navigate(['customer', 'dashboard']);
          }
          this.toastr.success('Welcome back');
        }
      },
      (error: any) => {
        this.error = error.error.error;
      }
    )
  }

  private setAuthCookie(token: string): void {
    const expirationDays = 1;
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + expirationDays);

    this.cookieService.set('authorization', token, expirationDate, '/');
  }

}
