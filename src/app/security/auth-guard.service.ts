import {ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {inject, Injectable} from '@angular/core';
import {TokenStorageService} from './token-storage.service';
import {NotificationService} from './notification-service';
import { LOGIN } from '../util/roots';

@Injectable({providedIn: 'root'})
export class AuthGuardService implements CanActivate {
  private readonly router = inject(Router);
  private readonly tokenService = inject(TokenStorageService);
  private readonly notificationService = inject(NotificationService);

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const resp = this.tokenService.getResponse();
    if(resp)
      return true;

    void this.router.navigate(['/', LOGIN], {
      queryParams: { returnUrl: state.url },
    });
    globalThis.sessionStorage.clear();
    this.notificationService.showSnackBar("Token Expired")
    return false;
  }
}
