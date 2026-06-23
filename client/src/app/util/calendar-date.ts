import type { CalendarDate } from '../DTO/types';
import {Pipe, PipeTransform} from '@angular/core';

export function formatLocalCalendarDate(d: Date): CalendarDate {
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  return `${y}-${m}-${day}`;
}

@Pipe({
  name: 'calendarDate',
})
export class CalendarDateFormatter implements PipeTransform {
    transform(value:Date) {
        return formatLocalCalendarDate(value);
    }

}
