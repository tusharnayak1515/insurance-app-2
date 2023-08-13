import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, map } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';

export const noAuthGuard = () => {
  const router = inject(Router);
  const userService: UserService = inject(UserService);
  return userService.getLoggedIn().pipe(
    map((value:boolean) => {
      if (value) {
        const user = JSON.parse(localStorage.getItem("user_details")!);
        if(user?.role === "admin") {
          router.navigate(['admin','dashboard']);
        }
        else {
          router.navigate(['customer','dashboard']);
        }
        return false;
      } else {
        return true;
      }
    },
    catchError((error: any) => {
      console.error('Error checking login status:', error);
      router.navigate(['customer-login']);
      return of(false);
    })
    )
  );
};
