import {Injectable} from '@angular/core';
import {UserRole} from '../DTO/types';
import UserProfileDto from '../DTO/entity_dto/user/user-profile.dto';

const TOKEN_KEY = "auth-token";
const RESPONSE_KEY = "success-response";
const USER_KEY = "auth-user";
const ROLE_KEY = "role";
const AVATAR_KEY = "avatar";

@Injectable({providedIn:'root'})
export class TokenStorageService{
  public saveToken(token:string){
    globalThis.sessionStorage.removeItem(TOKEN_KEY);
    globalThis.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken():string|null{
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveResponse(success: boolean ){
    globalThis.sessionStorage.removeItem(RESPONSE_KEY);
    globalThis.sessionStorage.setItem(RESPONSE_KEY, JSON.stringify(success));
  }

  public getResponse() : boolean{
    return JSON.parse(<string>sessionStorage.getItem(RESPONSE_KEY));
  }

  public saveUser(user:UserProfileDto){
    globalThis.sessionStorage.removeItem(USER_KEY);
    globalThis.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    this.setRole(user);
  }

  public getUser(): UserProfileDto | null {
    const raw = globalThis.sessionStorage.getItem(USER_KEY);
    if (raw == null || raw.trim() === '') {
      return null;
    }
    try {
      return JSON.parse(raw) as UserProfileDto;
    } catch {
      return null;
    }
  }

  public setRole(user:UserProfileDto|null){
    if(user) {
      globalThis.sessionStorage.removeItem(ROLE_KEY);
      globalThis.sessionStorage.setItem(ROLE_KEY, user.role);
    }
    else {
      globalThis.sessionStorage.removeItem(ROLE_KEY);
      globalThis.sessionStorage.setItem(ROLE_KEY, 'GUEST')
    }
  }
  public setAvatar(file:Blob){
    globalThis.sessionStorage.removeItem(ROLE_KEY);
    globalThis.sessionStorage.setItem(ROLE_KEY, JSON.stringify(file));
  }

  public getAvatar():Blob{
    return JSON.parse(<string>globalThis.sessionStorage.getItem(ROLE_KEY));
}
  public getRole(): UserRole{
    return JSON.parse(<UserRole>globalThis.sessionStorage.getItem(ROLE_KEY))
  }

  public logout(){
    globalThis.sessionStorage.clear();
    globalThis.location.reload();
    this.setRole(null);
  }
}
