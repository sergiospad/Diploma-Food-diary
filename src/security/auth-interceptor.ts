import {inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
const TOKEN_HEADER_KEY = 'Authorization';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptor implements HttpInterceptor {
  private readonly tokenService = inject(TokenStorageService);

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      let authReq = req;
      let token = this.tokenService.getToken();
      if(token!=null){
        authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, "Bearer "+token)});
      }
      return next.handle(authReq);
    }

}
