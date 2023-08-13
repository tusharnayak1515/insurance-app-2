import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import RegisterUser from 'src/app/models/RegisterUser';
import User from 'src/app/models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<any>(
    localStorage.getItem('user_details') ? true : false
  );
  public user: BehaviorSubject<User | null> = new BehaviorSubject<any>(
    localStorage.getItem('user_details') ? JSON.parse(localStorage.getItem('user_details')!) : null
  );
  public BACKEND_URL: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // FOR CUSTOMER ONLY
  public customerRegister = (user: RegisterUser): Observable<User> => {
    return this.http.post<User>(`${this.BACKEND_URL}/api/customer/register`, user);
  };

  // COMMON FOR ALL USERS (ADMIN AND CUSTOMER)
  public userLogin = (email: string, password: string): Observable<User> => {
    return this.http.post<User>(`${this.BACKEND_URL}/api/customer/login`, {email, password});
  };

  // COMMON FOR ALL USERS (ADMIN AND CUSTOMER)
  public updateProfile = (user: User): Observable<User> => {
    return this.http.put<User>(`${this.BACKEND_URL}/api/customer/update`, user);
  };

  // COMMON FOR ALL USERS (ADMIN AND CUSTOMER)
  public changePassword = (oldPassword:string,newPassword:string,confirmPassword: string): Observable<User> => {
    return this.http.put<User>(`${this.BACKEND_URL}/api/customer/change-password`, {oldPassword, newPassword, confirmPassword});
  };

  // SET THE USER
  public setUser(user: User | null): void {
    this.user.next(user);
  }

  // GET THE USER DETAILS
  public getUser(): Observable<User | null> {
    return this.user.asObservable();
  }

  // SET THE LOGGED IN
  public setLoggedIn(val: boolean): void {
    this.isLoggedIn.next(val);
  }

  // GET THE LOGGED IN
  public getLoggedIn(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

}
