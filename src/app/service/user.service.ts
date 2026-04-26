import {inject, Injectable} from '@angular/core';
import {Endpoint} from '../util/endpoint';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import UpdatePasswordRequest from '../DTO/requests/update-password.request';
import UserProfileDto from '../DTO/entity_dto/user/user-profile.dto';
import UserEditDto from '../DTO/entity_dto/user/user-edit.dto';
import ChangeRoleDto from '../DTO/entity_dto/user/change-role.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly userAPI = new Endpoint('user');

  getCurrentUser():Observable<UserProfileDto>{
    return this.http.get<UserProfileDto>(this.userAPI.builder().points("profile").build());
  }

  updateUser(user:UserEditDto):Observable<any>{
    return this.http.patch<UserEditDto>(this.userAPI.builder().points("update").build(), user);
  }

  updatePassword(req:UpdatePasswordRequest):Observable<any>{
    return this.http.patch<UpdatePasswordRequest>(this.userAPI.builder().points("password").build(), req);
  }

  changeRole(changeRole:ChangeRoleDto):Observable<any>{
    return this.http.patch<ChangeRoleDto>(this.userAPI.builder().points("role").build(), changeRole);
  }



}
