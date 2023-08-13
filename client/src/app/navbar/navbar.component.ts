import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';
import User from '../models/User';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  showSidebar: boolean = false;
  isLoggedIn:boolean = false;
  isAdmin:boolean = false;

  constructor(private router: Router, private userService:UserService, private cookieService:CookieService) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.showSidebar =
        event.url.slice(1).startsWith('admin/') ||
        event.url.slice(1).startsWith('customer/');
      }
    });

    this.userService.getUser().subscribe((value:User | null) => {
      if(value) {
        this.isLoggedIn = true;
        if(value?.role === "admin") {
          this.isAdmin = true;
        }
        else {
          this.isAdmin = false;
        }
      }
      else {
        this.isLoggedIn = false;
      }
    });
  }

  toggleMenu() {
    this.showSidebar = !this.showSidebar;
  }

  onLogout = ()=> {
    this.userService.setLoggedIn(false);
    this.userService.setUser(null);
    this.cookieService.set('authorization', "", new Date(), '/');
    localStorage.removeItem("user_details");
    this.router.navigate(['customer-login']);
  }
}
