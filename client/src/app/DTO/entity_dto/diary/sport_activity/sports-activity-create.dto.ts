import {CalendarDate} from '../../../types';

export default interface SportsActivityCreateDTO{
  name:string;
  calories: number;
  diaryRecordDate:CalendarDate;
}
