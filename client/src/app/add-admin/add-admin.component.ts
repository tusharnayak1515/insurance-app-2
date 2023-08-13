import { Component, OnInit } from '@angular/core';
import { AdminService } from '../services/admin/admin.service';

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

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    
  }

  addAdmin() {
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
        }
      },
      (error:any) => {
        console.error('Error adding admin', error);
      }
    );
  }
}

