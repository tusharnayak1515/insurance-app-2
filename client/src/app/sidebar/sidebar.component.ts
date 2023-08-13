import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user/user.service';
import User from '../models/User';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent implements OnInit {
  username:String = "";
  isAdmin:boolean = false;

  constructor(private userService:UserService) {}

  ngOnInit(): void {
    this.userService.getUser().subscribe((value:User | null) => {
      this.username = value?.username || "";
      if(value?.role === "admin") {
        this.isAdmin = true;
      }
      else {
        this.isAdmin = false;
      }
    });
  }
}
