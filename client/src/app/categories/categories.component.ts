import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category/category.service';
import Policycategory from '../models/Policycategory';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories:any[] = [];
  policyCategory: Policycategory = new Policycategory(null,"");
  category:Policycategory | null = null;

  constructor(private categoryService:CategoryService) {

  }

  ngOnInit(): void {
  this.categoryService.viewAllCategories().subscribe(
    (response:any)=> {
      if(response?.success) {
        this.categories = response?.list;
      }
    },
    (error:any)=> {
      console.log(error);
    }
  )
  }

  addCategory() {
    this.categoryService.addCategory(this.policyCategory).subscribe(
      (response:any)=> {
        if(response?.success) {
          this.categories = response?.list;
          this.policyCategory = new Policycategory(null,"");
        }
      },
      (error: any)=> {
        console.log(error);
      }
    );
  }

  onEdit(category:any) {
    this.category = category;
  }

  updateCategory() {
    this.categoryService.updateCategory(this.category).subscribe(
      (response:any)=> {
        if(response?.success) {
          this.categories = response?.list;
        }
      },
      (error: any)=> {
        console.log(error);
      }
    );
  }

  deleteCategory(categoryId:number) {
    this.categoryService.deleteCategory(categoryId).subscribe(
      (response:any)=> {
        if(response?.success) {
          this.categories = response?.list;
        }
      },
      (error: any)=> {
        console.log(error);
      }
    );
  }

}
