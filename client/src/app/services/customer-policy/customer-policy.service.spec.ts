import { TestBed } from '@angular/core/testing';

import { CustomerPolicyService } from './customer-policy.service';

describe('CustomerPolicyService', () => {
  let service: CustomerPolicyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerPolicyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
