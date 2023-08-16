import { Component, OnInit } from '@angular/core';
import { AdminService } from '../services/admin/admin.service';
import User from '../models/User';
import { UserService } from '../services/user/user.service';
import { ToastrService } from 'ngx-toastr';

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
    private toastr: ToastrService
    ) {
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
          this.toastr.success("Updation successfull");
        }
      },
      (error:any)=> {
        console.log(error);
        this.getAllCustomers();
      }
    );
  }

  deleteCustomer(customerId: number) {
    console.log(customerId);
    this.adminService.deleteCustomer(customerId).subscribe(
      (response:any)=> {
        if(response.success) {
          this.customers = response?.users;
          this.toastr.success("Deletion successfull");
        }
      },
      (error:any)=> {
        console.log(error);
        this.toastr.error("Deletion failed: This customer has applied to a policy");
        this.getAllCustomers();
      }
    );
  }
}
