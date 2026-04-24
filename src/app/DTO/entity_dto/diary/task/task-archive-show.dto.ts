import {TaskStatus, TaskTarget} from '../../../types';

export default interface TaskArchiveShowDTO{
  id: number;
  caloriesDeficit: number;
  currentTarget: TaskTarget;
  beginningDate: Date;
  endingDate: Date;
  status: TaskStatus;
}
