import { Component, OnInit } from '@angular/core';
import { PolicyService } from '../services/policy/policy.service';
import { CustomerPolicyService } from '../services/customer-policy/customer-policy.service';

@Component({
  selector: 'app-policy-history',
  templateUrl: './policy-history.component.html',
  styleUrls: ['./policy-history.component.css']
})
export class PolicyHistoryComponent implements OnInit {

  appliedPolicies: any[] = [];

  constructor(private customerPolicyService: CustomerPolicyService) { }

  ngOnInit(): void {
    this.loadAppliedPolicies();
  }

  loadAppliedPolicies(): void {
    this.customerPolicyService.viewMyPolicies().subscribe(
      (response: any) => {
        this.appliedPolicies = response?.customerPolicies;
      },
      (error: any) => {
        console.error('Error fetching applied policies:', error);
      }
    );
  }
}




