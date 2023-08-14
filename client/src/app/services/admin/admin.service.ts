import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import User from 'src/app/models/User';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  [x: string]: any;
  public BACKEND_URL: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  public addAdmin = ({username,email,mobile,password}: any): Observable<any> => {
    return this.http.post<any>(`${this.BACKEND_URL}/api/admin/register`, {username,email,mobile,password});
  };

  public updateCustomer = (customer:User | null): Observable<any> => {
    return this.http.put<any>(`${this.BACKEND_URL}/api/admin/update`, customer);
  }

  public deleteCustomer = (customerId: number): Observable<any> => {
    return this.http.delete<any>(`${this.BACKEND_URL}/api/admin/delete/${customerId}`);
  };

  public getAllCustomers = (): Observable<User[]> => {
    return this.http.get<any>(`${this.BACKEND_URL}/api/admin/view-customers`);
  }
}
