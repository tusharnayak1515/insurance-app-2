import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { tap } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';

export const adminGuard = () => {
  const router = inject(Router);
  const userService: UserService = inject(UserService);
  return userService.getLoggedIn().pipe(
    tap((value:boolean) => {
      if (value) {
        const user = JSON.parse(localStorage.getItem("user_details")!);
        if(user?.role === "customer") {
          router.navigate(['customer','dashboard']);
          return false;
        }
        return true;
      } else {
        router.navigate(['customer-login']);
        return false;
      }
    })
  );
};