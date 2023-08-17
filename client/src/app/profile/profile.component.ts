import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user/user.service';
import User from 'src/app/models/User';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  updatedName: string = '';
  updatedEmail: string = '';
  updatedMobile: string = '';
  updatedAddress: string = '' ;
  oldPassword: string = '' ;
  newPassword: string = '' ;
  confirmPassword: string  = '' ;
  constructor(private userService: UserService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.fetchUserProfile();
  }

  fetchUserProfile(): void {
    this.userService.getUser().subscribe((user: User | null) => {
      console.log(user)
      if (user) {
        this.user = user;
        this.updatedName = user.username;
        this.updatedEmail = user.email;
        this.updatedMobile = user.mobile;
        this.updatedAddress = user.address || "";
      }
    });
  }

  updateProfile(): void {
    if (this.user) {
      const updatedUser: User = {
        ...this.user,
        username: this.updatedName,
        email: this.updatedEmail,
        mobile: this.updatedMobile,
        address: this.updatedAddress 
      };

      this.userService.updateProfile(updatedUser).subscribe(
        (response: any) => {
          if(response.success) {
            localStorage.setItem("user_details", JSON.stringify(response?.user));
            this.userService.setUser(response?.user); 
            this.fetchUserProfile(); 
            this.toastr.success("Profile updated successfully");
          }
        },
        (error: any) => {
          console.error(error.error.error);
          this.toastr.error(error.error.error.error);
        }
      );
    }
  }

  changePassword() {
    const passwordRegex =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])/;
    if(this.oldPassword.replace("/\s/g","").trim().length === 0) {
      this.toastr.warning("Old password cannot be empty");
    }
    else if(!passwordRegex.test(this.newPassword)) {
      this.toastr.warning("Invalid new password");
    }
    else if(this.newPassword !== this.confirmPassword) {
      this.toastr.warning("Password mismatch");
    }
    else {
      this.userService.changePassword(this.oldPassword,this.newPassword,this.confirmPassword).subscribe(
        (response:any)=> {
          if(response?.success) {
            this.oldPassword = '' ;
            this.newPassword = '' ;
            this.confirmPassword = '' ;
            this.toastr.success("Password updated successfully");
          }
        },
        (error:any)=> {
          console.log(error);
          this.toastr.error(error.error.error);
        }
      );
    }
  }
}