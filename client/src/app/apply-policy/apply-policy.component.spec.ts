import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyPolicyComponent } from './apply-policy.component';

describe('ApplyPolicyComponent', () => {
  let component: ApplyPolicyComponent;
  let fixture: ComponentFixture<ApplyPolicyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplyPolicyComponent]
    });
    fixture = TestBed.createComponent(ApplyPolicyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
