import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category/category.service';
import Policycategory from '../models/Policycategory';
import { UserService } from '../services/user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: any[] = [];
  policyCategory: Policycategory = new Policycategory(null, "");
  category: Policycategory | null = null;
  isAdmin: boolean = false;

  constructor(private categoryService: CategoryService, private userService: UserService, private toastr: ToastrService) {

  }

  ngOnInit(): void {
    this.categoryService.viewAllCategories().subscribe(
      (response: any) => {
        if (response?.success) {
          this.categories = response?.list;
        }
      },
      (error: any) => {
        console.log(error);
      }
    );

    this.userService.getUser().subscribe((value:any)=> {
      if(value) {
        if(value?.role === "admin") {
          this.isAdmin = true;
        }
        else {
          this.isAdmin = false;
        }
      }
    });
  }

  addCategory() {
    this.categoryService.addCategory(this.policyCategory).subscribe(
      (response: any) => {
        if (response?.success) {
          this.categories = response?.list;
          this.policyCategory = new Policycategory(null, "");
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  onEdit(category: any) {
    this.category = category;
  }

  updateCategory() {
    this.categoryService.updateCategory(this.category).subscribe(
      (response: any) => {
        if (response?.success) {
          this.categories = response?.list;
        }
      },
      (error: any) => {
        this.toastr.error("Updation failed: Policies exist with this category");
        console.log(error);
      }
    );
  }

  deleteCategory(categoryId: number) {
    this.categoryService.deleteCategory(categoryId).subscribe(
      (response: any) => {
        if (response?.success) {
          this.categories = response?.list;
        }
      },
      (error: any) => {
        this.toastr.error("Deletion failed: Policies exist with this category");
        console.log(error);
      }
    );
  }

}
