import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import Policycategory from 'src/app/models/Policycategory';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  addCategory(category: Policycategory): Observable<Policycategory> {
    return this.http.post<Policycategory>(`${this.apiUrl}/api/policycategories/`, category);
  }

  viewAllCategories(): Observable<Policycategory[]> {
    return this.http.get<Policycategory[]>(`${this.apiUrl}/api/policycategories/`);
  }

  viewCategory(categoryId: number): Observable<Policycategory> {
    return this.http.get<Policycategory>(`${this.apiUrl}/api/policycategories/${categoryId}`);
  }

  updateCategory(category: any): Observable<Policycategory> {
    return this.http.put<Policycategory>(`${this.apiUrl}/api/policycategories/${category!.categoryId}`, category);
  }

  deleteCategory(categoryId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/api/policycategories/${categoryId}`);
  }
}