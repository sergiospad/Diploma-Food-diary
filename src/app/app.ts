import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavigationComponent} from './components/navigation/navigation';
import {FooterComponent} from './components/footer/footer';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavigationComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('food-diary-client');
}
