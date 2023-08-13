import { Component, AfterViewInit, OnInit } from '@angular/core';
import { PolicyService } from '../services/policy/policy.service'; // Import your PolicyService here
import { CategoryService } from '../services/category/category.service';

@Component({
  selector: 'app-policies',
  templateUrl: './policies.component.html',
  styleUrls: ['./policies.component.css']
})
export class PoliciesComponent implements OnInit {

  policies: any[] = [];
  categories: any[] = [];
  policyName: string = "";
  policydesc: string = "";
  categoryId: number = 0;
  sumAssured: number = 0;
  premiumAmount: number = 0;
  tenure: number = 0;
  policy:any = null;

  constructor(private policyService: PolicyService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.viewAllPolicies();
    this.categoryService.viewAllCategories().subscribe(
      (response: any) => {
        if (response?.success) {
          this.categories = response?.list;
          this.categoryId = this.categories[0]?.categoryId || 0;
        }
      },
      (error: any) => {
        console.log(error);
      },
    );
  }

  deletePolicy(policyId:number) {
    this.policyService.deletePolicy(policyId).subscribe(
      (response: any) => {
        if (response?.success) {
          this.policies = response?.list;
        }
      },
      (error: any) => {
        console.log(error);
      },
    );
  }

  addPolicy() {
    this.policyService.addPolicy(this.policyName, this.policydesc, this.categoryId, this.sumAssured, this.premiumAmount, this.tenure).subscribe(
      (response) => {
        if (response.success) {
          this.policies = response?.list;
          this.policyName = "";
          this.policydesc = "";
          this.categoryId = 0;
          this.sumAssured = 0;
          this.premiumAmount = 0;
          this.tenure = 0;
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  viewAllPolicies() {
    this.policyService.getAllPolicies().subscribe(
      (response: any) => {
        if (response?.success) {
          this.policies = response?.list;
        }
      },
      (error: any) => {
        console.log(error);
      },
    );
  }

  onEdit(policy:any) {
    const mypolicy = {
      policyId: policy.policyId,
      policyName: policy.policyName,
      policydesc: policy.policydesc,
      categoryId: policy.policycategory.categoryId,
      sumAssured: policy.sumAssured,
      premiumAmount: policy.premiumAmount,
      tenure: policy.tenure,
    }
    this.policy = mypolicy;
  }

  updatePolicy() {
    this.policyService.updatePolicy(this.policy).subscribe(
      (response:any)=> {
        if(response.success) {
          this.policies = response?.list;
        }
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }
}