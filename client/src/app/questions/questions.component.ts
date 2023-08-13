import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../services/question/question.service';
import Question from 'src/app/models/Question';


@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {

  questions: any[] = [];
  question: any = null;

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.getAllQuestions();
  }

  getAllQuestions(): void {
    this.questionService.getAllQuestions().subscribe(
      (response:any) => {
        console.log(response);
        this.questions = response.list;
      },
      (error:any) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  onEdit(question:any) {
    this.question = question;
  }

  answerQuestion() {
    this.questionService.answerQuestion(this.question).subscribe(
      (response:any) => {
        console.log(response);
        this.questions = response.list;
      },
      (error:any) => {
        console.error('Error answering the question:', error);
      }
    );
  }
  
}
