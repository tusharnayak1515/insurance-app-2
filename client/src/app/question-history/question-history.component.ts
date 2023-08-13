import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../services/question/question.service';



@Component({
  selector: 'app-question-history',
  templateUrl: './question-history.component.html',
  styleUrls: ['./question-history.component.css']
})
export class QuestionHistoryComponent implements OnInit {

  history: any[] = [];

  constructor(
    private questionService: QuestionService
  ) { }

  ngOnInit(): void {
    this.getQuestionAndHistory();
  }
  getQuestionAndHistory(): void {
    this.questionService.getQuestionHistory().subscribe(
      (response:any) => {
        if(response.success) {
          this.history = response.list;
        }
      },
      (error:any) => {
        console.error('Error fetching question history:', error);
      }
    );
  }
}  