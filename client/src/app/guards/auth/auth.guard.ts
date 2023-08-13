import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { tap } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';

export const authGuard = () => {
  const router = inject(Router);
  const userService: UserService = inject(UserService);
  return userService.getLoggedIn().pipe(
    tap((value:boolean) => {
      if (value) {
        return true;
      } else {
        router.navigate(['customer-login']);
        return false;
      }
    })
  );
};
