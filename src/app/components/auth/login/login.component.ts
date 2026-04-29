
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import AuthService from '../../../service/auth.service';
import {TokenStorageService} from '../../../security/token-storage.service';
import {NotificationService} from '../../../security/notification-service';
import {Router, RouterLink} from '@angular/router';
import {UserService} from '../../../service/user.service';
import ImageModelService from '../../../service/recipe_resource/image-model.service';
import AvatarIndexedDb from '../../../image_services/avatar-indexed.db';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {FEED_ROOT, REGISTER} from '../../../util/roots'
import {httpErrorMessage} from '../../../util/error.service';
import {catchError, switchMap, tap} from 'rxjs/operators';
import {EMPTY, from} from 'rxjs';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export default class LoginComponent implements OnInit {
  public loginForm!: FormGroup;
  private readonly authService = inject(AuthService);
  private readonly tokenService = inject(TokenStorageService);
  private readonly notificationService = inject(NotificationService);
  private readonly router = inject(Router);
  private readonly formBuilder = inject(FormBuilder);
  private readonly userService = inject(UserService);
  private readonly imageModelService = inject(ImageModelService);
  private readonly avatarRep = inject(AvatarIndexedDb);

  ngOnInit(): void {
    this.loginForm = this.createLoginForm();
  }

  createLoginForm():FormGroup{
    return this.formBuilder.group({
      username:['', Validators.compose([Validators.required])],
      password:['', Validators.compose([Validators.required])]
    })
  }

  submit(): void {
    this.authService.authenticateUser({
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    }).subscribe({
        next: (data) => this.succeedAuth(data),
        error: (error: unknown) => {
          const message = httpErrorMessage(error, 'Login failed');
          this.notificationService.showSnackBar(message);
        },
      }
    );
  }

  private succeedAuth(data: { token: string; success?: boolean }): void {
    this.tokenService.saveToken(data.token);
    this.tokenService.saveResponse(data.success ?? true);
    this.userService.getCurrentUser().pipe(
      tap((user) => this.tokenService.saveUser(user)),
      switchMap((user) => this.imageModelService.getImage(user.avatarID)),
      switchMap((blob) => from(this.avatarRep.saveBlob(blob))),
      tap(() => this.notificationService.showSnackBar('Successfully logged in')),
      tap(() => {
        void this.router.navigate(['/', FEED_ROOT]).then(() => globalThis.location.reload());
      }),
      catchError((error) => {
        const message = httpErrorMessage(error, 'Failed to load profile');
        this.notificationService.showSnackBar(message);
        return EMPTY;
      }),
    ).subscribe();
  }

  protected readonly REGISTER = REGISTER;
}

