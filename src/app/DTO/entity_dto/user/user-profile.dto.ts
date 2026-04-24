import {UserRole} from '../types';

export default interface UserProfileDto{
  id:number;
  username:string;
  avatarID:number;
  role:UserRole;
}
