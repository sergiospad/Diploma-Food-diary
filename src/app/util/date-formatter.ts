import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatter implements PipeTransform {
  transform(value: string |Date):string {
    let date = new Date(value);
    return `${this.padStart(date.getDay())}.${this.padStart(date.getMonth())} в ${this.padStart(date.getHours())}:${this.padStart(date.getMinutes())}`
  }

  private padStart(num:number):string{
    return num.toString().padStart(2, '0');
  }
}
