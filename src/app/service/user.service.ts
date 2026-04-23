import {inject, Injectable} from '@angular/core';
import {EndpointBuilder} from '../util/endpoint-builder';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserProfileDto} from '../DTO/user/user-profile.dto';
import {UserEditDto} from '../DTO/user/user-edit.dto';
import {UpdatePasswordRequest} from '../DTO/requests/update-password.request';
import {ChangeRoleDto} from '../DTO/user/change-role.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly userAPI = new EndpointBuilder('user');

  getCurrentUser():Observable<UserProfileDto>{
    return this.http.get<UserProfileDto>(this.userAPI.build("profile"));
  }

  updateUser(user:UserEditDto):Observable<any>{
    return this.http.patch<UserEditDto>(this.userAPI.build("update"), user);
  }

  updatePassword(req:UpdatePasswordRequest):Observable<any>{
    return this.http.patch<UpdatePasswordRequest>(this.userAPI.build("password"), req);
  }

  changeRole(changeRole:ChangeRoleDto):Observable<any>{
    return this.http.patch<ChangeRoleDto>(this.userAPI.build("role"), changeRole);
  }



}
