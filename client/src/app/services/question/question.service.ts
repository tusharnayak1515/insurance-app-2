import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Question from 'src/app/models/Question'; 

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private backendUrl: string = 'http://localhost:8080'; 

  constructor(private http: HttpClient) { }

  getAllQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.backendUrl}/api/question/`);
  }

  getQuestionHistory(): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.backendUrl}/api/question/myquestions`);
  }

  askQuestion(questionText: string): Observable<any> {
    return this.http.post(`${this.backendUrl}/api/question/`, {questionText});
  }

  answerQuestion(question:any):Observable<any> {
    return this.http.put(`${this.backendUrl}/api/question/${question.questionId}`, {answerText: question.answerText});
  }
}
