import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { UserService } from './services/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'client';
  showSidebar: boolean = false;

  constructor(private router: Router, private userService:UserService) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.showSidebar =
        event.url.slice(1).startsWith('admin/') ||
        event.url.slice(1).startsWith('customer/');
      }
    });

    this.userService.getLoggedIn().subscribe((value:boolean)=> {
      if(!value) {
        localStorage.removeItem("user_details");
      }
    });
  }
}
