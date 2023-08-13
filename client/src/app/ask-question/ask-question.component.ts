import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../services/question/question.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {

  questionText:string = "";

  constructor(private questionService: QuestionService, private router: Router) { }

  ngOnInit(): void {
  }

  askQuestion(): void {
    if (!this.questionText || this.questionText.replace("/\s/g","").trim().length === 0) {
      console.error('Question text cannot be empty.');
    }
    else {
      this.questionService.askQuestion(this.questionText).subscribe(
        (response) => {
          if(response.success) {
            this.questionText = "";
            this.router.navigate(['customer','question-history']);
          }
        },
        error => {
          console.error('Error asking question:', error);
        }
      );
    }

  }
}


