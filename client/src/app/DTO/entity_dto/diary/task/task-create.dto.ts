import {TaskTarget} from '../../../types';

export default interface TaskCreateDTO{
  target: TaskTarget;
  targetWeight: number;
  caloriesDeficit: number;
  beginningDate: Date;
}
