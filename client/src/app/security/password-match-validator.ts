import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
export const passwordsMatchValidator: ValidatorFn = (
  group: AbstractControl,
): ValidationErrors | null => {
  const pass = group.get('password')?.value;
  const confirm = group.get('confirmPassword')?.value;
  if (pass === confirm) {
    return null;
  }
  return { passwordsMismatch: true };
};
