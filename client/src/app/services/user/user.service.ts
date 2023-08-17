import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable } from 'rxjs';
import RegisterUser from 'src/app/models/RegisterUser';
import User from 'src/app/models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private cookieService:CookieService) { }

  public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<any>(
    this.cookieService.get('authorization') ? true : false
  );
  public user: BehaviorSubject<User | any> = new BehaviorSubject<any>(
    localStorage.getItem('user_details') ? JSON.parse(localStorage.getItem('user_details')!) : null
  );
  public BACKEND_URL: string = 'http://localhost:8080';

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
  public setUser(user: User | any): void {
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
