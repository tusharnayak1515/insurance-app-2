import { Component, OnInit } from '@angular/core';
import { AdminService } from '../services/admin/admin.service';
import { NavigationStart, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import User from '../models/User';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-view-customers',
  templateUrl: './view-customers.component.html',
  styleUrls: ['./view-customers.component.css']
})
export class ViewCustomersComponent implements OnInit {
  customers: any[] = [];
  viewEditForm:boolean = false;
  user:User | null = null;
  customer:User | null = null;

  constructor(
    private adminService: AdminService,
    private userService: UserService,
    private router: Router,
    private cookieService: CookieService) {
  }

  ngOnInit(): void {
    this.getAllCustomers();
    this.userService.getUser().subscribe(
      (val:any)=> {
        this.user = val;
      }
    )
  }

  onEdit(customer:User) {
    this.viewEditForm = true;
    this.customer = customer;
  }

  getAllCustomers() {
    this.adminService.getAllCustomers().subscribe(
      (response: any) => {
        if (response?.success) {
          this.customers = response.users;
        }
      },
      (error: any) => {
        console.log("error in view customers.ts: ", error);
      }
    )
  }

  updateCustomer() {
    this.adminService.updateCustomer(this.customer).subscribe(
      (response:any)=> {
        if(response.success) {
          this.customers = response?.users;
        }
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

  deleteCustomer(customerId: number) {
    console.log(customerId);
    this.customers = this.customers.filter(customer => customer.userId !== customerId);
  }
}
