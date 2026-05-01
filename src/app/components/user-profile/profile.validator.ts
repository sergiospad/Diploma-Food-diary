import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export default class UserProfileValidator {

  static readonly heightCmValidator: ValidatorFn = (
    control: AbstractControl,
  ): ValidationErrors | null => {
    const raw = control.value;
    if (raw === null || raw === undefined || raw === '') {
      return null;
    }
    const n = Number(raw);
    if (!Number.isFinite(n)) {
      return {notNumber: true};
    }
    const min = 50;
    const max = 272;
    if (n < min || n > max) {
      return {heightRange: {min, max, actual: n}};
    }
    return null;
  };

  static readonly birthdateValidator: ValidatorFn = (
    control: AbstractControl,
  ): ValidationErrors | null => {
    const raw = control.value;
    const t = this.birthdateToTimeMs(raw);
    if (!Number.isFinite(t)) {
      return {notDate: true};
    }
    const min = new Date(1920, 0, 1).getTime();
    const endOfToday = new Date();
    endOfToday.setHours(23, 59, 59, 999);
    const max = endOfToday.getTime();
    if (t < min || t > max) {
      return {dateRange: true};
    }
    return null;
  };

  private static birthdateToTimeMs(raw: unknown): number {
    if (raw instanceof Date) {
      const t = raw.getTime();
      return Number.isNaN(t) ? NaN : t;
    }
    if (typeof raw === 'string' || typeof raw === 'number') {
      const t = new Date(raw).getTime();
      return Number.isNaN(t) ? NaN : t;
    }
    return NaN;
  }
}
