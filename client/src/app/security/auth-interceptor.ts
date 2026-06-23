import {inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
const TOKEN_HEADER_KEY = 'Authorization';

/** Регистрируется в `app.config` через `HTTP_INTERCEPTORS` (см. `provideHttpClient(withInterceptorsFromDi())`). */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly tokenService = inject(TokenStorageService);

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      let authReq = req;
      const token = this.tokenService.getToken()?.trim();
      if (token != null && token !== '') {
        authReq = req.clone({
          headers: req.headers.set(TOKEN_HEADER_KEY, `Bearer ${token}`),
        });
      }
      return next.handle(authReq);
    }

}
