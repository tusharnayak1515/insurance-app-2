import { Component, OnInit } from '@angular/core';
import { PolicyService } from '../services/policy/policy.service';
import { CustomerPolicyService } from '../services/customer-policy/customer-policy.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-apply-policy',
  templateUrl: './apply-policy.component.html',
  styleUrls: ['./apply-policy.component.css']
})
export class ApplyPolicyComponent implements OnInit {

  policies: any[] = [];
  appliedPolicies: any[] = [];

  constructor(private policyService:PolicyService, private customerPolicyService:CustomerPolicyService, private router:Router) {

  }

  ngOnInit(): void {
    this.getAllPolicies();
    this.viewAppliedPolicies();
  }

  getAllPolicies() {
    this.policyService.getAllPolicies().subscribe(
      (response:any)=> {
        if(response.success) {
          console.log(response.list);
          this.policies = response?.list;
        }
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

  applyPolicy(policyId: any): void {
    this.customerPolicyService.applyPolicy(policyId).subscribe(
      (response:any)=> {
        if(response.success) {
          this.appliedPolicies = response?.customerPolicies;
          this.router.navigate(['customer','policy-history']);
        }
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

  viewAppliedPolicies() {
    this.customerPolicyService.viewMyPolicies().subscribe(
      (response) => {
        if (response.success) {
          console.log(response?.customerPolicies)
          this.appliedPolicies = response?.customerPolicies;
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
}

