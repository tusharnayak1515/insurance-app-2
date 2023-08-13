import { Component } from '@angular/core';
interface Policy {
  serialNo: number;
  policyName: string;
  category: string;
  sumAssurance: number;
  premium: number;
  tenure: number;
  creationDate: string;
}


@Component({
  selector: 'app-apply-policy',
  templateUrl: './apply-policy.component.html',
  styleUrls: ['./apply-policy.component.css']
})
export class ApplyPolicyComponent {
  policies: Policy[] = [
    {
      serialNo: 1,
      policyName: 'jeevan A',
      category: 'life insurance',
      sumAssurance: 100000,
      premium: 5000,
      tenure: 12,
      creationDate: 'Aug 9, 2023'
    },
    {
      serialNo: 2,
      policyName: 'health shield',
      category: 'health insurance',
      sumAssurance: 50000,
      premium: 3000,
      tenure: 24,
      creationDate: 'Aug 10, 2023'
    },
    {
      serialNo: 3,
      policyName: 'vehicle protect',
      category: 'auto insurance',
      sumAssurance: 20000,
      premium: 1500,
      tenure: 6,
      creationDate: 'Aug 11, 2023'
    }
    
  ];

  applyPolicy(policy: Policy): void {
    // Update the policy status as applied in the in-memory database
    const appliedPolicy = { ...policy, applied: true };
    this.updateDatabase(appliedPolicy);

    console.log(`Applied policy: ${policy.policyName}`);
    // Add your logic here, such as sending notifications, etc.
  }

  updateDatabase(updatedPolicy: Policy): void {
    // In a real scenario, you would call an API to update the database
    // For demonstration, let's assume we have a simple in-memory database array
    const policyIndex = this.policies.findIndex(p => p.serialNo === updatedPolicy.serialNo);
    if (policyIndex !== -1) {
      this.policies[policyIndex] = updatedPolicy;
    }
  }
}

