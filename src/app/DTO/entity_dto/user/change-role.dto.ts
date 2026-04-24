import {UserRole} from '../types';

export default interface ChangeRoleDto {
  userId: number;
  role: UserRole;
}
