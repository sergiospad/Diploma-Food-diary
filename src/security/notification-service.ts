import {inject, Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class NotificationService{
  private readonly snackbar = inject(MatSnackBar);
  public showSnackBar(message:string):void{
    this.snackbar.open(message, "", {
      duration: 2000
    });
  }
}
