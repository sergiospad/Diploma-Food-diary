import {UserRole} from '../types';

export interface UserProfileDto{
  id:number;
  username:string;
  avatarID:number;
  role:UserRole;
}
