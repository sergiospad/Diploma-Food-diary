import {UserRole} from '../types';

export interface ChangeRoleDto {
  userId: number;
  Role: UserRole;
}
