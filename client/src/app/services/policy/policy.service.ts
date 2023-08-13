import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PolicyService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllPolicies(): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/policy/`);
  }

  getAppliedPolicies(): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/customer-policy/mypolicies`);
  }

  deletePolicy(policyId: number): Observable<any> {
    const url = `${this.apiUrl}/api/policy/${policyId}`;
    return this.http.delete(url);
  }

  addPolicy(policyName:string,policydesc:string,categoryId:number,sumAssured:number,premiumAmount:number,tenure:number): Observable<any> {
    const url = `${this.apiUrl}/api/policy/`;
    return this.http.post(url, {policyName,policydesc,categoryId,sumAssured,premiumAmount,tenure});
  }

  updatePolicy(policy: any): Observable<any> {
    console.log(policy);
    const url = `${this.apiUrl}/api/policy/${policy.policyId}`;
    return this.http.put(url, policy);
  }
}