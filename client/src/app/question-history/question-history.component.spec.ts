import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionHistoryComponent } from './question-history.component';

describe('QuestionHistoryComponent', () => {
  let component: QuestionHistoryComponent;
  let fixture: ComponentFixture<QuestionHistoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionHistoryComponent]
    });
    fixture = TestBed.createComponent(QuestionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
