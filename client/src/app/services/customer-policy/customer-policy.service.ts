import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerPolicyService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http:HttpClient) { }
  
  public viewAllCustomerPolicies(): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/api/customer-policy/`);
  }
  
  public viewMyCustomerPolicies(): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/api/customer-policy/mypolicies`);
  }

  public approvePolicy(id:number):Observable<any> {
    return this.http.put<any[]>(`${this.apiUrl}/api/customer-policy/approve/${id}`, {});
  }

  public rejectPolicy(id:number):Observable<any> {
    return this.http.put<any[]>(`${this.apiUrl}/api/customer-policy/reject/${id}`, {});
  }

}