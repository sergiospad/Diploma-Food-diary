import {Component, inject, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {UserService} from '../../../service/user.service';
import {NotificationService} from '../../../security/notification-service';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {passwordsMatchValidator} from '../../../security/password-match-validator';
import {MatButton} from '@angular/material/button';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import UpdatePasswordRequest from '../../../DTO/requests/update-password.request';

@Component({
  selector: 'app-change-password',
  imports: [
    FormsModule,
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css',
})
export class ChangePassword implements OnInit {
  private readonly dialogRef = inject(MatDialogRef<ChangePassword>);
  private readonly userService= inject(UserService);
  private readonly notificationService = inject(NotificationService);
  public passwordForm!:FormGroup;
  public fb = inject(FormBuilder);

  ngOnInit(): void {
    this.passwordForm = this.createForm();
  }

  createForm():FormGroup{
    return this.fb.group({
      oldPassword: ["", Validators.required],
      password: ["", Validators.required],
      confirmPassword: ["", Validators.required],
    },
      { validators: passwordsMatchValidator },)
  }

  protected passwordsMismatchVisible(): boolean {
    const g = this.passwordForm;
    if (!g) {
      return false;
    }
    let password =  g.get('password');
    let confirm = g.get('confirmPassword');
    const touched =
      password?.dirty ||
      confirm?.dirty || confirm?.touched;
    return g.hasError('passwordsMismatch') && !!touched;
  }

  protected cancel(): void {
    this.dialogRef.close();
  }

  protected changePassword(): void {
    const req = {
      oldPassword: this.passwordForm.controls['oldPassword'].value,
      newPassword: this.passwordForm.controls['password'].value,
    } as UpdatePasswordRequest;
    this.userService.updatePassword(req).subscribe({
      next: () => {
        this.notificationService.showSnackBar('Пароль обновлен');
        this.dialogRef.close(true);
      },
    });
  }
}
