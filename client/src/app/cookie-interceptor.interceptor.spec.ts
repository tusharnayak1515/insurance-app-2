import { TestBed } from '@angular/core/testing';

import { CookieInterceptorInterceptor } from './cookie-interceptor.interceptor';

describe('CookieInterceptorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      CookieInterceptorInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: CookieInterceptorInterceptor = TestBed.inject(CookieInterceptorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
