import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class CookieInterceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const cookieValue = this.cookieService.get('authorization');
    if (cookieValue) {
      request = request.clone({
        withCredentials: true,
        setHeaders: {
          Authorization: cookieValue,
        }
      });
    }
    return next.handle(request);
  }
}
