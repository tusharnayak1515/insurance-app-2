import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../services/question/question.service';

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
  }

  askQuestion(questionText: string): void {
    if (!questionText || questionText.trim().length === 0) {
      console.error('Question text cannot be empty.');
      return;
    }

    this.questionService.askQuestion(questionText).subscribe(
      response => {
        console.log('Question asked successfully.');
      },
      error => {
        console.error('Error asking question:', error);
      }
    );
  }
}


