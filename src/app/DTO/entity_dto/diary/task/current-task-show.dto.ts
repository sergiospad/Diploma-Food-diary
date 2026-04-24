import {TaskTarget} from '../../../types';

export default interface CurrentTaskShowDTO{
  id:number;
  startWeight:number;
  targetWeight:number;
  currentWeight:number;
  numberOfDays:number;
  taskTarget:TaskTarget;
  caloriesDeficit:number;

}
