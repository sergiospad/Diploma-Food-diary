export type UserGender = 'M'|"FM";

export interface UserEditDto{
  id:number;
  username:string;
  email:string;
  gender: UserGender;
  birthdate: Date;
  height: number;
}
