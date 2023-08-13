import { Component, OnInit } from '@angular/core';
import { PolicyService } from '../services/policy/policy.service';

@Component({
  selector: 'app-policy-history',
  templateUrl: './policy-history.component.html',
  styleUrls: ['./policy-history.component.css']
})
export class PolicyHistoryComponent implements OnInit {

  appliedPolicies: any[] = [];

  constructor(private policyService: PolicyService) { }

  ngOnInit(): void {
    this.loadAppliedPolicies();
  }

  loadAppliedPolicies(): void {
    this.policyService.getAppliedPolicies().subscribe(
      (policies: any[]) => {
        this.appliedPolicies = policies;
      },
      (error: any) => {
        console.error('Error fetching applied policies:', error);
      }
    );
  }
}




