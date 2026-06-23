import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import AuthService from '../../../service/auth.service';
import {NotificationService} from '../../../security/notification-service';
import {Router, RouterLink} from '@angular/router';
import { LOGIN } from '../../../util/roots';
import {passwordsMatchValidator} from '../../../security/password-match-validator';
import {MatError} from '@angular/material/form-field';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {httpErrorMessage} from '../../../util/error.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    MatError,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export default class RegisterComponent implements OnInit {

  public registerForm!: FormGroup;
  private readonly authService = inject(AuthService);
  private readonly notificationService = inject(NotificationService);
  private readonly router = inject(Router);
  private readonly formBuilder = inject(FormBuilder);

  protected readonly LOGIN = LOGIN;

  protected passwordsMismatchVisible(): boolean {
    const g = this.registerForm;
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

  ngOnInit(): void {
    this.registerForm = this.createRegisterForm();
  }

  createRegisterForm(): FormGroup {
    return this.formBuilder.group({
        username: ['', Validators.compose([Validators.required])],
        email: ['', Validators.compose([Validators.required, Validators.email])],

        password: ['', Validators.compose([Validators.required])],
        confirmPassword: ['', Validators.compose([Validators.required])],
      },
      { validators: passwordsMatchValidator },
    );
  }

  register(): void {
    if (this.registerForm.invalid) {
      return;
    }
    this.authService
      .registerUser({
        username: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
      })
      .subscribe({
        next: () => {
          void this.router.navigate(['/']).then(() => {
            this.notificationService.showSnackBar('Successfully registered');
            globalThis.location.reload();
          });
        },
        error: (error: unknown) => {
          this.notificationService.showSnackBar(
            httpErrorMessage(error, 'Registration failed'),
          );
        },
      });
  }
}
