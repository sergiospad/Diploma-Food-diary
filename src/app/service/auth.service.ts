import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../util/endpoint';
import LoginRequest from '../DTO/requests/login.request';
import {Observable} from 'rxjs';
import JWTTokenResponse from '../DTO/responce/jwt-token.response';
import SignupRequest from '../DTO/requests/signup.request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly authAPI = new Endpoint('auth');

  authenticateUser(req: LoginRequest):Observable<any>{
    return this.http.post<any>(
      this.authAPI.builder()
        .points("signin")
        .build(),
      req)
  }

  registerUser(req: SignupRequest): Observable<any>{
    return this.http.post<any>(
      this.authAPI.builder()
        .points("signup")
        .build(),
      req)
  }
}
