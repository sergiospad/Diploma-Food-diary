import {Component, inject} from '@angular/core';
import {TokenStorageService} from '../../security/token-storage.service';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-user-profile',
  imports: [],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css',
})
export class UserProfile {
  tokenService = inject(TokenStorageService);
  userService = inject(UserService);
}
