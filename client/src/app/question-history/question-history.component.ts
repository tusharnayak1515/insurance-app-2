import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { QuestionService } from '../services/question/question.service';



@Component({
  selector: 'app-question-history',
  templateUrl: './question-history.component.html',
  styleUrls: ['./question-history.component.css']
})
export class QuestionHistoryComponent implements OnInit {

  question: any;
  history: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private questionService: QuestionService
  ) { }

  ngOnInit(): void {
    this.getQuestionAndHistory();
  }
  getQuestionAndHistory(): void {
    this.questionService.getQuestionHistory().subscribe(
      (response:any) => {
        if (Array.isArray(response) && response.length > 0) {
          this.question = response[0]; 
          this.history = response.slice(1); 
        } else {
          console.error(' invalid response received.');
        }
      },
      (error:any) => {
        console.error('Error fetching question history:', error);
      }
    );
  }
}  