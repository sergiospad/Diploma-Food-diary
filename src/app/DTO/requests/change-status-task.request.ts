import {TaskStatus} from '../types';

export default interface ChangeStatusTaskRequest{
  id: number;
  status: TaskStatus;
  endingDate: Date;
}
