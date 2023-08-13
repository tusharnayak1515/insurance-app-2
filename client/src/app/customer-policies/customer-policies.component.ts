import { Component, OnInit } from '@angular/core';
import { CustomerPolicyService } from '../services/customer-policy/customer-policy.service';

@Component({
  selector: 'app-customer-policies',
  templateUrl: './customer-policies.component.html',
  styleUrls: ['./customer-policies.component.css']
})
export class CustomerPoliciesComponent implements OnInit {

  customerPolicies:any[] = [];

  constructor(private customerPolicyService: CustomerPolicyService) {

  }

  ngOnInit(): void {
    this.getAllCustomePolicies();
  }

  getAllCustomePolicies() {
    this.customerPolicyService.viewMyPolicies().subscribe(
      (response:any)=> {
        this.customerPolicies = response.customerPolicies;
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

  approvePolicy(id:number) {
    this.customerPolicyService.approvePolicy(id).subscribe(
      (response:any)=> {
        this.customerPolicies = response.customerPolicies;
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

  rejectPolicy(id:number) {
    this.customerPolicyService.rejectPolicy(id).subscribe(
      (response:any)=> {
        this.customerPolicies = response.customerPolicies;
      },
      (error:any)=> {
        console.log(error);
      }
    );
  }

}
