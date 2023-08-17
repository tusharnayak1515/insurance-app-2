import { Component, OnInit } from '@angular/core';
import { AdminService } from '../services/admin/admin.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css']
})
export class AddAdminComponent implements OnInit {
  username: string = '';
  email: string = '';
  mobile : string = '';
  password : string = '';
  error : string = '';

  constructor(private adminService: AdminService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    
  }

  addAdmin() {
    const passwordRegex =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])/;
    if(!passwordRegex.test(this.password)) {
      this.error = "Enter a strong password";
    }
    else {
      const adminData = {
        username: this.username,
        email: this.email,
        password : this.password,
        mobile:this.mobile
      };
  
      this.adminService.addAdmin(adminData).subscribe(
        (response:any) => {
          if(response.success) {
            this.username = '';
            this.email = '';
            this.password = '';
            this.mobile = '';
            this.error = '';
            this.router.navigate(['admin','dashboard']);
            this.toastr.success("Admin created successfully");
          }
        },
        (error:any) => {
          this.toastr.error(error.error.error || "Add admin failed");
          console.error('Error adding admin', error);
        }
      );
    }

  }
}

